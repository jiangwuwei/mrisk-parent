/*
 * Copyright (c) 2016-2020 LEJR.COM All Right Reserved
 */

package com.zoom.risk.gateway.service;


import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;


/**
 * 风控网关统一服务
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年8月22日
 */
public interface RiskFraudFacade {

	/**
	 * 风控检测
	 * @param data 目标数据。
	 * @return
	 * 	风险检测结果。
	 */
	public RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskData);
	
	public RpcResult<Object> statusChangeInform (Map<String, Object> resultData);
}
