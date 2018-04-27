package com.zoom.risk.gateway.core.api.impl;

import com.zoom.risk.gateway.decision.api.RiskDTreeApi;
import com.zoom.risk.gateway.core.service.RiskDTreeFacade;
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
@Service("riskDTreeApi")
public class RiskDTreeApiImpl implements RiskDTreeApi {
	private static final Logger logger = LogManager.getLogger(RiskDTreeApiImpl.class);

	@Resource(name="riskDTreeFacade")
	private RiskDTreeFacade riskDTreeFacade;

	public RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskInput){
		return riskDTreeFacade.evaluate(riskInput);
	}

	public RpcResult<Object> statusChangeInform (Map<String,Object> riskInput){
		return riskDTreeFacade.statusChangeInform(riskInput);

	}
}
