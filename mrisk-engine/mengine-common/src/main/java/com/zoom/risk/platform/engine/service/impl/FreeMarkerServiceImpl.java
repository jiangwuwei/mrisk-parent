/**
 * 
 */
package com.zoom.risk.platform.engine.service.impl;

import java.io.StringWriter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zoom.risk.platform.engine.service.FreeMarkerService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */

@Service("freeMarkerService")
public class FreeMarkerServiceImpl implements FreeMarkerService {
	private static final Logger logger = LogManager.getLogger(FreeMarkerServiceImpl.class);
	private Configuration config;
	
	public FreeMarkerServiceImpl(){
	}
	
	public void setConfig(Configuration config){
		this.config = config;
		config.setClassicCompatible(true);
	}
	
	public String merge(String key, Map<String,Object> riskInput){
		String result = null;
		StringWriter writer = new StringWriter();
		try{
			Template template = config.getTemplate(key,"utf-8");
			template.process(riskInput, writer);
			result = writer.getBuffer().toString();
		}catch(Exception e){
			logger.error("FreeMarkerService.merge  happens error Template key : " + key + " , input : " + riskInput );
		}
		return result;
	}
}
