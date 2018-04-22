package com.zoom.risk.gateway.fraud.api;


import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;

/**
 * 风控网关dubbo服务接口。
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年8月22日
 */
public interface RiskFraudApi {
	/**
	 * 风控检测。
	 * 
	 * @param riskInput 目标数据。
	 * @return
	 * 		风险检测结果。
	 */
	RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskInput);

	/**
	 * 业务操作结果回传操作
	 * 必须传入字段有
	 * 场景号 scene, 风控流水号 riskId, 业务操作的状态  status
	 * @param riskInput
	 * @return
	 */
	RpcResult<Object> statusChangeInform (Map<String,Object> riskInput);
}
