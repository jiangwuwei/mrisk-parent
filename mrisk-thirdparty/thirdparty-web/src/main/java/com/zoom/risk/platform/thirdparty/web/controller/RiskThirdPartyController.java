package com.zoom.risk.platform.thirdparty.web.controller;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.thirdparty.api.ThirdPartyApi;
import com.zoom.risk.platform.thirdparty.web.utils.RequestJsonReader;
import com.zoom.risk.platform.thirdparty.web.utils.RestResult;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 27, 2015
 */
@RestController
@RequestMapping("/thirdparty")
public class RiskThirdPartyController {
	private static final Logger logger = LogManager.getLogger(RiskThirdPartyController.class);

	@Resource(name="thirdPartyApi")
	private ThirdPartyApi thirdPartyApi;
	
	@RequestMapping(value="/invoke",method=RequestMethod.POST,headers="Content-Type=application/json")
	public RestResult<Map<String,Object>> invoke(HttpServletRequest request) {
		RestResult<Map<String,Object>> riskResult = new RestResult();
		Map<String, Object> riskInput = null;
		try {
			String json = RequestJsonReader.readRiskData(request);
			riskInput = JSON.parseObject(json,  new TypeToken<HashMap<String,Object>>(){}.getType());
			RpcResult<Map<String, Object>> rpcResult = thirdPartyApi.invoke(String.valueOf(riskInput.get("serviceName")), riskInput);
			riskResult.setData(rpcResult.getData());
		} catch (JsonSyntaxException e) {
			logger.error("Json数据格式解析错误",e);
			return RestResult.paramMissing("Json数据格式解析错误");
		} catch (Exception e) {
			logger.error("Restful api happens errors",e);
			riskResult = RestResult.systemError(e.getMessage());
		}
		return riskResult;
	}
}
