/**
 * 
 */
package com.zoom.risk.platform.engine.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.jade.api.JadeDataApi;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.datacenter.service.QuotaCollectorService;
import com.zoom.risk.platform.engine.api.DecisionQuota;
import com.zoom.risk.platform.engine.api.DecisionResponse;
import com.zoom.risk.platform.engine.api.RuleEngineApi;
import com.zoom.risk.platform.engine.mode.EnginePolicy;
import com.zoom.risk.platform.engine.mode.EnginePolicyWrapper;
import com.zoom.risk.platform.engine.service.PolicyCacheService;
import com.zoom.risk.platform.engine.service.PolicyCommonExecutorService;
import com.zoom.risk.platform.engine.service.PolicyExecutorService;
import com.zoom.risk.platform.engine.service.RiskExtendService;
import com.zoom.risk.platform.engine.utils.EngineConstants;
import com.zoom.risk.platform.engine.vo.Policy;
import com.zoom.risk.platform.engine.vo.PolicyRouter;
import com.zoom.risk.platform.engine.vo.Quota;
import com.zoom.risk.platform.engine.vo.Rule;
import com.zoom.risk.platform.es.service.EsActionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jiangyulin
 *Nov 12, 2015
 */
@Service("ruleEngineApi")
public class RuleEngineApiImpl implements RuleEngineApi {
	private static final Logger logger = LogManager.getLogger(RuleEngineApiImpl.class);
    //事件处理的状态 默认为 0;  0: 成功 1: 数据校验异常 2: 调用风控引擎异常
    public static final String GATEWAY_STATUS_OK = "0";
    public static final String GATEWAY_STATUS_PARAM_ERROR = "1";
    public static final String GATEWAY_STATUS_SYSTEM_ERROR = "2";

    @Resource(name="policyCommonExecutorService")
    private PolicyCommonExecutorService policyCommonExecutorService;

	@Resource(name="jadeDataApi")
	private JadeDataApi jadeDataApi;

	@Resource(name="policyCacheService")
	private PolicyCacheService policyCacheService;
	
	@Resource(name="quotaCollectorService")
	private QuotaCollectorService quotaCollectorService;
	
	@Resource(name="esClientThreadPoolExecutor")
	private ThreadPoolTaskExecutor esClientThreadPoolExecutor;
	
	@Resource(name="esActionService")
	private EsActionService esActionService;

	@Resource(name="policyExecutorService")
	private PolicyExecutorService policyExecutorService;

    @Resource(name="riskExtendService")
	private RiskExtendService riskExtendService;

    /**
     * 如果没有配置策略或者规则，返回错误，不发送到kafka
     * @param riskInput
     * @return
     */
	public DecisionResponse evaluate(Map<String, Object> riskInput){
		DecisionResponse response = new DecisionResponse();
		PolicyRouter router = null;
		long takingTime = System.currentTimeMillis();
        final long startingTime = takingTime;
		Map<String, Object> quotaValues = null;
        String gatewayStatus = GATEWAY_STATUS_OK;
        Policy policy = null;
        String sceneNo = riskInput.get(EngineConstants.ENGINE_SCENE).toString().substring(0, 4);
        LsManager.getInstance().check();
		try {
            riskExtendService.decorate(riskInput);
            Set<Rule> hitRules = new HashSet<>();
            int decisionCode = 3;
            long quotaTime = 0;
            final Map<String, Object> cloneRiskInput = jadeDataApi.convert2DatabaseType(riskInput);
            long convertTime = System.currentTimeMillis() - takingTime;
            {
                //通用策略集合
                EnginePolicy enginePolicy = policyCommonExecutorService.getCommonPolicy(sceneNo);
                policy = enginePolicy.getPolicy();
                Set<Rule> rules = enginePolicy.getRules();
                Set<Quota> quotasSet = enginePolicy.getQuotas();
                decisionCode = policyExecutorService.execute(cloneRiskInput, rules, policy, hitRules);
            }
            {
                if ( decisionCode == Rule.DECISION_CODE_PASS_1) {
                    EnginePolicyWrapper wrapper = policyCacheService.getPolicy(sceneNo, cloneRiskInput);
                    EnginePolicy enginePolicy = wrapper.getEnginePolicy();
                    router = wrapper.getPolicyRouter();
                    DecisionResponse validateResult = this.validatePolicy(enginePolicy, cloneRiskInput);
                    if (!validateResult.isOK()) {
                        return validateResult;
                    }
                    policy = enginePolicy.getPolicy();
                    Set<Rule> rules = enginePolicy.getRules();
                    Set<Quota> quotasSet = enginePolicy.getQuotas();
                    if (!quotasSet.isEmpty()) {
                        quotaValues = quotaCollectorService.collectQuotas(quotasSet, cloneRiskInput);
                        cloneRiskInput.putAll(quotaValues);
                    }
                    quotaTime = System.currentTimeMillis() - takingTime;
                    policyExecutorService.execute(cloneRiskInput, rules, policy, hitRules);
                }
            }
			policyExecutorService.convert2Response(policy, hitRules, response);
			takingTime = System.currentTimeMillis() - takingTime;
			response.put(EngineConstants.ENGINE_TAKING_TIME, takingTime);
            logger.info("RuleEngineApi evaluating takes {} ms, take {} ms getting all quotas, converting time {} ms finalRiskInput:{} ", takingTime, quotaTime, convertTime, cloneRiskInput);
		}catch(Exception e){
            gatewayStatus = GATEWAY_STATUS_SYSTEM_ERROR;
            logger.error("RuleEngineApi happens error", e);
		}finally {
            riskInput.put("gatewayStatus",gatewayStatus);
		    this.sendKafkaMessage(riskInput, startingTime, response, router, quotaValues);
		}
		return response;
	}

    /**
     * validate the scene's config for rules
     * @param enginePolicy
     * @param riskInput
     * @return
     */
	private DecisionResponse validatePolicy(EnginePolicy enginePolicy, Map<String, Object> riskInput){
        if (enginePolicy == null) {
            logger.info("RuleEngineApi can not find policy or policy set for riskInput : {}", riskInput);
            return DecisionResponse.get404Error("can not find policy or policy set");
        }
        Policy policy = enginePolicy.getPolicy();
        if (policy == null) {
            logger.info("RuleEngineApi can not find policy for riskInput : {}", riskInput);
            return DecisionResponse.get404Error("can not find a policy");
        }
        Set<Rule> rules = enginePolicy.getRules();
        if (rules.isEmpty()) {
            logger.info("RuleEngineApi can not find rule for riskInput : {}", riskInput);
            return DecisionResponse.get404Error("can not find a rule");
        }
        return new DecisionResponse();
    }

	private void sendKafkaMessage(final Map<String, Object> riskInput, final long startingTime, final DecisionResponse response,  final PolicyRouter router, final Map<String, Object> finalQuotaValues){
        long evaculatingtime  = System.currentTimeMillis() - startingTime;
        esClientThreadPoolExecutor.submit(() -> {
            String detailJson = null;
            try {
                Map<String, Object> kafkaMap = RuleEngineApiImpl.this.shadowClone(riskInput);
                kafkaMap.put(EngineConstants.ENGINE_TAKING_TIME, evaculatingtime);
                if (response.getHitPolicy() != null) {
                    kafkaMap.put(EngineConstants.ENGINE_DECISION_CODE, response.getHitPolicy().getFinalDecisionCode() + "");
                }
                kafkaMap.put(EngineConstants.ENGINE_POLICY, response.getHitPolicy());
                kafkaMap.put(EngineConstants.ENGINE_POLICY_ROUTER, policyExecutorService.convertPolicyRouter(router));
                kafkaMap.put(EngineConstants.ENGINE_HIT_RULES, response.getHitRules());
                List<DecisionQuota> quotaList = new ArrayList<>();
                Map<String, Quota> quotaMap = policyCacheService.getQuotaMap();
                if (finalQuotaValues != null) {
                    finalQuotaValues.forEach((key, value) -> {
                        Quota q = quotaMap.get(key);
                        DecisionQuota dquota = new DecisionQuota();
                        dquota.setQuotaNo(key);
                        dquota.setQuotaValue(value);
                        if (q != null) {
                            dquota.setId(q.getId());
                            dquota.setName(q.getName());
                            dquota.setSourceType(q.getSourceType());
                            dquota.setQuotaDataType(q.getQuotaDataType());
                        }
                        quotaList.add(dquota);
                    });
                }
                kafkaMap.put(EngineConstants.ENGINE_QUOTA_VALUES, quotaList);
                detailJson = JSON.toJSONString(kafkaMap);
                logger.info("Risk Engine send kafka message [{}]", detailJson);
                esActionService.dispatchEvent(detailJson);
            } catch (Exception e) {
                logger.error("RuleEngineApi converting to json or sending kafka message happen error", e);
            }
            logger.info("RuleEngineApi evaluating and sending takes {} ms, detail json : [{}]", System.currentTimeMillis()-startingTime, detailJson);
        });
	}

	private Map<String,Object> shadowClone(Map<String,Object> finalRiskInput ){
	    return (Map<String,Object>)((HashMap<String,Object>)finalRiskInput).clone();
    }
}
