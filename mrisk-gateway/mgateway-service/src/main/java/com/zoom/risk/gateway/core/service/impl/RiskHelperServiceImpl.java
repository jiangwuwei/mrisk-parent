package com.zoom.risk.gateway.core.service.impl;

import com.zoom.risk.gateway.core.service.RiskHelperService;
import com.zoom.risk.gateway.common.utils.Utils;
import com.zoom.risk.gateway.fraud.utils.RiskConstant;
import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.gateway.common.utils.RiskUUIDFactory;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.common.rpc.RpcResults;
import com.zoom.risk.platform.engine.api.DecisionRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/12/8.
 */
@Service("riskHelperService")
public class RiskHelperServiceImpl implements RiskHelperService {
    private static final Logger logger = LogManager.getLogger(RiskHelperServiceImpl.class);


    /**make sure riskId has been put into return result should rule engine happen errors
     * @param result
     * @param riskId
     */
    @Override
    public void putRiskIfPossible(RpcResult<Map<String, Object>> result, String riskId){
        if ( result.getData() == null ) {
            Map<String, Object> map = new HashMap<>();
            map.put(RiskConstant.RISK_ID, riskId);
            result.setData(map);
        }
    }


    /**
     * convert hit rules to map model to adjust exiting mechanism
     * @param hitRules
     * @return
     */
    @Override
    public List<Map<String,Object>> convertHitRules(List<DecisionRule> hitRules){
        List<Map<String,Object>> list = new ArrayList<>();
        if ( hitRules != null && hitRules.size() > 0 ) {
            hitRules.forEach((rule)->{
                Map<String,Object> map = new HashMap<>();
                list.add(map);
                map.put(RiskHelperService.HIT_RULE_ID, rule.getId());
                map.put(RiskHelperService.HIT_RULE_NAME, rule.getName());
                map.put(RiskHelperService.HIT_RULE_RULE_NO, rule.getRuleNo());
                map.put(RiskHelperService.HIT_RULE_DECISION, rule.getDecisionCode());
            });
        }
        return list;
    }

    /** to get riskLongDate, in normal case it should be always a non null long value
     * @param riskData
     * @return
     */
    @Override
    public String getRiskLongDate(Map<String, Object> riskData){
        Long time = (Long)riskData.get(RISK_LONG_DATE);
        if ( time == null ){
            time = Utils.getCurrentTimeMillis();
        }
        return time+"";
    }

    /**
     * prepare necessary data for risk gateway
     * @param riskScene
     * @param riskData
     * @return
     */
    @Override
    public String createRiskSystemData(String riskScene, Map<String, Object> riskData, String riskBusiType){
        String riskId  = RiskUUIDFactory.generateUUID(riskScene);
        riskData.put(RiskConstant.RISK_ID, riskId);
        long time = Utils.getCurrentTimeMillis();
        riskData.put(RiskConstant.RISK_DATE, Utils.getCurrentYYYYMMDDHHMMSS());
        //用于es的事件时间
        riskData.put(RISK_LONG_DATE, time);
        //用于es的事件类型
        riskData.put("riskType", RiskResult.RISK_TYPE_EVENT);
        //此次操作的结果.
        riskData.put(RiskConstant.RISK_STATUS, RiskResult.RISK_STATUS_INIT);
        //默认值为 1,表示通过, 不要返回第四种状态到调用方引起混淆
        riskData.put(RiskConstant.RESULT_DECISION_CODE, RiskResult.RISK_DECISION_CODE_PASS);
        riskData.put("riskBusiType", riskBusiType);
        return riskId;
    }


    /**
     * only for 010101
     * @param riskScene
     * @return
     */
    public RpcResult<Map<String,Object>> getSucccessulResult(String riskScene){
        Map<String, Object> checkResult = new HashMap<>();
        checkResult.put(RiskConstant.RESULT_DECISION_CODE,RiskResult.RISK_DECISION_CODE_PASS);
        checkResult.put(RiskConstant.RISK_ID,RiskUUIDFactory.generateUUID(riskScene));
        return RpcResults.success(checkResult);
    }
}
