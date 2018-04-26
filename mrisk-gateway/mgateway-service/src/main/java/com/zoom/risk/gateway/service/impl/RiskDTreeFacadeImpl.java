package com.zoom.risk.gateway.service.impl;

import com.google.gson.reflect.TypeToken;
import com.zoom.risk.gateway.service.RiskDTreeFacade;
import com.zoom.risk.gateway.extend.service.RiskExtendFramework;
import com.zoom.risk.gateway.service.RiskHelperService;
import com.zoom.risk.gateway.common.utils.GsonUtil;
import com.zoom.risk.gateway.common.utils.Utils;
import com.zoom.risk.gateway.fraud.utils.RiskConstant;
import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.common.rpc.RpcResults;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.decision.api.DTreeEngineApi;
import com.zoom.risk.platform.decision.api.DTreeResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 风控网关统一服务
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年9月11日
 */

@Service("riskDTreeFacade")
public class RiskDTreeFacadeImpl implements RiskDTreeFacade {
	private static final Logger logger = LogManager.getLogger(RiskDTreeFacadeImpl.class);
	public static final String TAKINGTIME = "takingTime";
	public static final String EXECUTING_ROUTE = "executingRoute";
	public static final String DEBUG = "debug";
	public static final String QUOTA_STR_VALUES = "quotaStrValues";


    @Resource(name="dtreeEngineApi")
    private DTreeEngineApi dtreeEngineApi;

	@Resource(name="riskHelperService")
	private RiskHelperService riskHelperService;

	@Resource(name="riskExtendFramework")
	private RiskExtendFramework riskExtendFramework;

	@Override
	public RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskData){
		long time = System.currentTimeMillis();
		RpcResult<Map<String,Object>> result = RpcResults.systemError("默认系统错误");
		String riskId = null;
		String riskScene = String.valueOf(riskData.get(RiskConstant.RISK_SCENE));
        String gatewayStatus = RiskResult.GATEWAY_STATUS_SYSTEM_ERROR;
		try {
			LsManager.getInstance().check();
			riskId =  riskHelperService.createRiskSystemData(riskScene, riskData, RiskResult.RISK_BUSI_TYPE_DECISION_TREE);
            final String originalInputJson = GsonUtil.getGson().toJson(riskData);
            logger.info("RiskDTreeFacade original input: [{}]",originalInputJson );
			//黑名单
			riskExtendFramework.decorate(riskData, RiskResult.RISK_BUSI_TYPE_DECISION_TREE);
			DTreeResponse response = dtreeEngineApi.evaluate(riskData);
			Map<String,Object> checkResult = new HashMap<>();
			checkResult.put(RiskConstant.RISK_ID, riskId);
			if ( response.isOK() ) {
				checkResult.put(RiskConstant.RESULT_DECISION_CODE, response.getDecisionCode());
				checkResult.put(RiskConstant.RESULT_ACTION_CODE, response.getActionCode());
				riskData.put(RiskConstant.RESULT_DECISION_CODE, response.getDecisionCode() +"");
				riskData.put(RiskConstant.RESULT_ACTION_CODE, response.getActionCode());
				riskData.put(TAKINGTIME,response.getExtendedValue(TAKINGTIME));
				if ( riskData.containsKey(DEBUG)) {
					//主要是过滤null对象
					List<Map<String,Object>> list = GsonUtil.getGson().fromJson(GsonUtil.getNoNullGson().toJson(response.getExtendedValue(EXECUTING_ROUTE)),new TypeToken<ArrayList<HashMap<String,Object>>>(){}.getType());
					checkResult.put(EXECUTING_ROUTE, list);
					checkResult.put(QUOTA_STR_VALUES, response.getExtendedValue(QUOTA_STR_VALUES));
				}
				gatewayStatus = RiskResult.GATEWAY_STATUS_OK;
				logger.info("RiskDTreeFacade's evaluate takes {} ms , Risk result json : {}", Utils.getCurrentTimeMillis()-time, GsonUtil.getGson().toJson(checkResult));
				result = RpcResults.success(checkResult);
			}else{
				result.setErrorCode(response.getResponseCode());
				result.setMessage(response.getResponseDesc());
				if ( riskData.containsKey(DEBUG)) {
					checkResult.put(EXECUTING_ROUTE, response.getExtendedValue(EXECUTING_ROUTE));
					checkResult.put(QUOTA_STR_VALUES, response.getExtendedValue(QUOTA_STR_VALUES));
				}
				result.setData(checkResult);
			}
		}catch (Throwable e) {
			gatewayStatus = RiskResult.GATEWAY_STATUS_SYSTEM_ERROR;
			logger.error("风控网关调用系统异常, scene:[" + riskScene +"], takes [" + (Utils.getCurrentTimeMillis()-time) + " ms]", e);
			result = RpcResults.systemError(e.getMessage());
		}finally{
			riskData.put("gatewayStatus", gatewayStatus);
			riskHelperService.putRiskIfPossible(result,riskId);
		}
		return result;
	}



	/**
	 * @param resultData
	 * @return
	 */
	public RpcResult<Object> statusChangeInform (Map<String, Object> resultData){
		String jsonMessage = null;
		RpcResult<Object> result = null;
		try {
			if ( resultData.get(RiskConstant.RISK_STATUS) != null ) {
				resultData.put(RiskConstant.RISK_STATUS, resultData.get(RiskConstant.RISK_STATUS) + "");
			}
			jsonMessage = GsonUtil.getGson().toJson(resultData);
			result = RpcResults.success(null);
		}catch (Throwable e) {
			logger.error("RiskDTreeFacade happens error",e);
			result = RpcResults.systemError(e.getMessage());
		}
		return result;
	}

}
