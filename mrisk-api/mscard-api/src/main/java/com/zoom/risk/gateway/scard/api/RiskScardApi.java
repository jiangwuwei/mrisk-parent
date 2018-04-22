package com.zoom.risk.gateway.scard.api;


import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;

/**
 * 风控网关dubbo服务接口。
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年8月22日
 */
public interface RiskScardApi {
	/**
	 * 风控检测。
	 *
	 * @param riskInput 目标数据。
	 * @return
	 * 		风险检测结果。
	 */
	RpcResult<Map<String,Object>> evaluate(Map<String, Object> riskInput);

}
