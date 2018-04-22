package com.zoom.risk.operating.web.controller;

import com.zoom.risk.platform.config.service.CuratorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {
	private static final Logger logger = LogManager.getLogger(ConfigController.class);

	@Resource(name="curatorService")
	private CuratorService curatorService;

	@RequestMapping(value="/refresh/cache")
	public Map<String,Object> refreshCache(@RequestParam(name="action",required =true)String action) {
		Map<String, Object> riskInput = new HashMap();
		int status = 1;
		try {
			curatorService.refresh(action);
		} catch (Exception e) {
			status= 0;
			logger.error("Restful api happens errors",e);
		}
		riskInput.put("status", status);
		return riskInput;
	}
}
