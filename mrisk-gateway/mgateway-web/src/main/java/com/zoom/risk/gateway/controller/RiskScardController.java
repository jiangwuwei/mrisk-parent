package com.zoom.risk.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.gateway.fraud.utils.RequestJsonReader;
import com.zoom.risk.gateway.fraud.utils.RiskConstant;
import com.zoom.risk.gateway.service.RiskSCardFacade;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.common.rpc.RpcResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/riskSCard")
public class RiskScardController {
	private static final Logger logger = LogManager.getLogger(RiskScardController.class);
	@Resource(name="riskSCardFacade")
	private RiskSCardFacade  riskSCardFacade;

	@RequestMapping(value="/v1/evaluate",method=RequestMethod.POST,headers="Content-Type=application/json")
	public RpcResult<Map<String,Object>> evaluateV1(HttpServletRequest request) {
		RpcResult<Map<String,Object>> riskResult = null;
		Map<String, Object> riskInput = null;
		try {
			String json = RequestJsonReader.readRiskData(request);
			riskInput = JSON.parseObject(json,  new TypeToken<HashMap<String,Object>>(){}.getType());
			//请求sourceIp 处理
			if ( ! riskInput.containsKey(RiskConstant.RISK_SOURCE_IP) ) {
				String sourceIpFromRestApi = request.getRemoteAddr();
				riskInput.put(RiskConstant.RISK_SOURCE_IP,sourceIpFromRestApi);
			}
			riskResult = riskSCardFacade.evaluate(riskInput);
		} catch (Exception e) {
			logger.error("Restful api happens errors",e);
			riskResult = RpcResults.systemError(e.getMessage());
		}
		return riskResult;
	}
}
