package com.zoom.risk.gateway.service.impl;

import com.zoom.risk.gateway.fraud.utils.RiskResult;
import com.zoom.risk.gateway.scard.utils.SCardConstant;
import com.zoom.risk.gateway.service.RiskExtendService;
import com.zoom.risk.gateway.service.RiskHelperService;
import com.zoom.risk.gateway.service.RiskSCardFacade;
import com.zoom.risk.gateway.service.utils.GsonUtil;
import com.zoom.risk.gateway.service.utils.Utils;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.common.rpc.RpcResults;
import com.zoom.risk.platform.scard.api.SCardEngineApi;
import com.zoom.risk.platform.scard.api.ScoreCardResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 风控网关统一服务
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年9月11日
 */

@Service("riskSCardFacade")
public class RiskSCardFacadeImpl implements RiskSCardFacade {
	private static final Logger logger = LogManager.getLogger(RiskSCardFacadeImpl.class);
	public static final String TAKINGTIME = "takingTime";

	/*
	@Resource(name="jadeDataApi")
	private JadeDataApi jadeDataApi;

	@Resource(name="riskPoolExecutor")
	private ThreadPoolTaskExecutor riskPoolExecutor;
	*/

    @Resource(name="scardEngineApi")
    private SCardEngineApi scardEngineApi;

	@Resource(name="riskHelperService")
	private RiskHelperService riskHelperService;

	@Resource(name="riskExtendService")
	private RiskExtendService riskExtendService;

	@Override
	public RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskData){
		RpcResult<Map<String,Object>> rpcResult = RpcResults.systemError("默认系统错误");
		String riskId = null;
		long time = Utils.getCurrentTimeMillis();
		String riskScene = String.valueOf(riskData.get(SCardConstant.RISK_SCENE));
        String gatewayStatus = RiskResult.GATEWAY_STATUS_SYSTEM_ERROR;
		try {
			riskId =  riskHelperService.createRiskSystemData(riskScene, riskData, RiskResult.RISK_BUSI_TYPE_SCARD);
            final String originalInputJson = GsonUtil.getGson().toJson(riskData);
            logger.info("RiskDTreeFacade original input: [{}]",originalInputJson );
			//黑名单
			riskExtendService.decorate(riskData, RiskResult.RISK_BUSI_TYPE_SCARD);
			ScoreCardResponse response = scardEngineApi.evaluate(riskData);
			Map<String,Object> checkResult = new HashMap<>();
			checkResult.put(SCardConstant.RISK_ID, riskId);
			if ( response.isOK() ) {
				checkResult.put(SCardConstant.ENGINE_SCARD_RULE_FINAL, response.getEngineScardRuleFinal());
				checkResult.put(SCardConstant.ENGINE_SCARD_SCORE, response.getEngineScardScore());
				riskData.put(SCardConstant.ENGINE_SCARD_RULE_FINAL, response.getEngineScardRuleFinal());
				riskData.put(SCardConstant.ENGINE_SCARD_SCORE, response.getEngineScardScore());
				long takingTime = Utils.getCurrentTimeMillis()-time;
				riskData.put(TAKINGTIME, takingTime );
				gatewayStatus = RiskResult.GATEWAY_STATUS_OK;
				logger.info("RiskSCardFacade's evaluate takes {} ms , Risk result json : {}", takingTime, GsonUtil.getGson().toJson(checkResult));
				rpcResult = RpcResults.success(checkResult);
			}else{
				rpcResult.setErrorCode(response.getResponseCode());
				rpcResult.setMessage(response.getResponseDesc());
				rpcResult.setData(checkResult);
			}
		}catch (Throwable e) {
			gatewayStatus = RiskResult.GATEWAY_STATUS_SYSTEM_ERROR;
			logger.error("风控网关调用系统异常, scene:[" + riskScene +"], takes [" + (Utils.getCurrentTimeMillis()-time) + " ms]", e);
			rpcResult = RpcResults.systemError(e.getMessage());
		}finally{
			riskData.put("gatewayStatus", gatewayStatus);
			riskHelperService.putRiskIfPossible(rpcResult,riskId);
			//this.saveEvent(riskData);
		}
		return rpcResult;
	}

	/*
	protected  void saveEvent(Map<String,Object> riskData){
		long saveEventAndSendKafka = System.currentTimeMillis();
		riskPoolExecutor.submit(() -> {
			String json = null;
			try {
				json =  GsonUtil.getNoNullGson().toJson(riskData);
				logger.info("SCardFacade input for jade: [{}]", json);
				jadeDataApi.insertEvent(riskData);
			} catch (Exception e) {
				logger.error("Jade insert operation happens error, risk input:[" + json + "]", e);
			}
			logger.info("SCardFacade's saveEvent takes {} ms", System.currentTimeMillis()-saveEventAndSendKafka);
		});
	}
	*/
}
