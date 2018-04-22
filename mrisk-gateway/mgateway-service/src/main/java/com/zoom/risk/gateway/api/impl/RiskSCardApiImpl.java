package com.zoom.risk.gateway.api.impl;

import com.zoom.risk.gateway.scard.api.RiskScardApi;
import com.zoom.risk.gateway.service.RiskSCardFacade;
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
@Service("riskScardApi")
public class RiskSCardApiImpl implements RiskScardApi {
	private static final Logger logger = LogManager.getLogger(RiskSCardApiImpl.class);

	@Resource(name="riskSCardFacade")
	private RiskSCardFacade riskSCardFacade;
	
	/**
	 * 风控检测。
	 * 
	 * @param riskInput 目标数据。
	 * @return
	 * 		风险检测结果。
	 */
	public RpcResult<Map<String,Object>> evaluate(Map<String,Object> riskInput){
		return riskSCardFacade.evaluate(riskInput);
		
	}

}
