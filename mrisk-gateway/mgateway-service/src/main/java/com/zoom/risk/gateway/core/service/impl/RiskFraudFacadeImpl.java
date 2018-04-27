package com.zoom.risk.gateway.core.service.impl;

import com.zoom.risk.gateway.core.proxy.RuleEngineProxy;
import com.zoom.risk.gateway.extend.service.RiskExtendFramework;
import com.zoom.risk.gateway.core.service.RiskFraudFacade;
import com.zoom.risk.gateway.core.service.RiskHelperService;
import com.zoom.risk.gateway.common.utils.GsonUtil;
import com.zoom.risk.gateway.common.utils.Utils;
import com.zoom.risk.gateway.fraud.utils.RiskConstant;
import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.jade.api.JadeDataApi;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.common.rpc.RpcResults;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 风控网关统一服务
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年9月11日
 */

@Service("riskFraudFacade")
public class RiskFraudFacadeImpl implements RiskFraudFacade {
	private static final Logger logger = LogManager.getLogger(RiskFraudFacadeImpl.class);

	@Resource(name="jadeDataApi")
	private JadeDataApi jadeDataApi;
	
	@Resource(name="riskExtendFramework")
	private RiskExtendFramework riskExtendFramework;

	@Resource(name="riskPoolExecutor")
	private ThreadPoolTaskExecutor riskPoolExecutor;

    @Resource(name="riskHelperService")
    private RiskHelperService riskHelperService;

	@Resource(name="zoomRuleEngine")
	private RuleEngineProxy zoomRuleEngine;

	@Override
	public RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskData){
		long time = System.currentTimeMillis();
		RpcResult<Map<String,Object>> result = RpcResults.systemError("默认系统错误");
		String riskId = null;
		String riskScene = String.valueOf(riskData.get(RiskConstant.RISK_SCENE));
        String gatewayStatus = RiskResult.GATEWAY_STATUS_OK;
		try {
			LsManager.getInstance().check();
			riskId =  riskHelperService.createRiskSystemData(riskScene, riskData, RiskResult.RISK_BUSI_TYPE_ANTIFRAUD);
			riskExtendFramework.decorate(riskData, RiskResult.RISK_BUSI_TYPE_ANTIFRAUD);
            final String originalInputJson = GsonUtil.getGson().toJson(riskData);
            logger.info("RiskGateway original input: [{}]",originalInputJson );
            Map<String, Object> checkResult = zoomRuleEngine.evaluate(riskData);
            checkResult.put(RiskConstant.RISK_ID, riskId);
            gatewayStatus = RiskResult.GATEWAY_STATUS_OK;
            logger.info("RiskGateway's evaluate takes {} ms , Risk result json : {}", Utils.getCurrentTimeMillis()-time, GsonUtil.getGson().toJson(checkResult));
			return RpcResults.success(checkResult);
		}catch (Throwable e) {
			gatewayStatus = RiskResult.GATEWAY_STATUS_SYSTEM_ERROR;
			logger.error("风控网关调用系统异常, scene:[" + riskScene +"], takes [" + (Utils.getCurrentTimeMillis()-time) + " ms]", e);
			result = RpcResults.systemError(e.getMessage());
		}finally{
			riskData.put("gatewayStatus", gatewayStatus);
			riskHelperService.putRiskIfPossible(result,riskId);
			//风控数据入库与发送Kafka消息
            this.saveEvent(riskData);
		}
		return result;
	}

	/**
	 * @param resultData
	 * @return
	 */
	public RpcResult<Object> statusChangeInform (Map<String, Object> resultData){
		String jsonMessage = null;
		try {
		    if ( resultData.get(RiskConstant.RISK_STATUS) != null ) {
                resultData.put(RiskConstant.RISK_STATUS, resultData.get(RiskConstant.RISK_STATUS) + "");
            }
			jsonMessage = GsonUtil.getGson().toJson(resultData);
            logger.info("Jade2db update: {}", jsonMessage );
			jadeDataApi.updateEvent(resultData);
			return RpcResults.success(null);
		}catch (Throwable e) {
			logger.error("风控网关调用系统异常",e);
			return RpcResults.systemError(e.getMessage());
		}
	}

	/**
	 * @param riskData
	 */
	protected  void saveEvent(Map<String,Object> riskData){
        long saveEventAndSendKafka = System.currentTimeMillis();
		riskPoolExecutor.submit(() -> {
            String json = null;
            try {
                json =  GsonUtil.getNoNullGson().toJson(riskData);
                logger.info("RiskGateway input for jade: [{}]", json);
				jadeDataApi.insertEvent(riskData);
            } catch (Exception e) {
                logger.error("Jade insert operation happens error, risk input:[" + json + "]", e);
            }
            logger.info("RiskGateway's saveEvent takes {} ms", System.currentTimeMillis()-saveEventAndSendKafka);
        });
	}

}
