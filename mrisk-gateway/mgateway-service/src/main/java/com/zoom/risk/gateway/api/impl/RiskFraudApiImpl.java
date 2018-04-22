package com.zoom.risk.gateway.api.impl;

import com.zoom.risk.gateway.fraud.api.RiskFraudApi;
import com.zoom.risk.gateway.service.RiskFraudFacade;
import com.zoom.risk.platform.common.rpc.RpcResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 风控网关远程服务接口实现。
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年8月22日
 */
@Service("riskFraudApi")
public class RiskFraudApiImpl implements RiskFraudApi {
	private static final Logger logger = LogManager.getLogger(RiskFraudApiImpl.class);

	@Resource(name="riskFraudFacade")
	private RiskFraudFacade riskFraudFacade;

	
	/**
	 * 风控检测。
	 * 
	 * @param riskInput 目标数据。
	 * @return
	 * 		风险检测结果。
	 */
	public RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskInput){
		return riskFraudFacade.evaluate(riskInput);
		
	}

	/**
	 * 业务操作结果回传操作
	 * 必须传入字段有
	 * 场景号 scene, 风控流水号 riskId, 业务操作的状态  status
	 * @param riskInput
	 * @return
	 */
	public RpcResult<Object> statusChangeInform (Map<String,Object> riskInput){
		return riskFraudFacade.statusChangeInform(riskInput);
		
	}
}
