package com.zoom.risk.gateway.decision.api;



import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;

/**
 * 风控网关dubbo服务接口。
 * @author jiangyulin
 * @version 2.0 
 * @date 2015/5/18
 */
public interface RiskDTreeApi {

	RpcResult<Map<String,Object>> evaluate(Map<String, Object> riskInput);

	RpcResult<Object> statusChangeInform (Map<String,Object> riskInput);

}
