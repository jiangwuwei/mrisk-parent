/*
 * Copyright (c) 2015-2020 LEJR.COM All Right Reserved
 */

package com.zoom.risk.gateway.core.service;



import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;

/**
 * 风控网关统一服务
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年8月22日
 */
public interface RiskSCardFacade {

	public RpcResult<Map<String,Object>> evaluate(Map<String, Object> riskData);


}
