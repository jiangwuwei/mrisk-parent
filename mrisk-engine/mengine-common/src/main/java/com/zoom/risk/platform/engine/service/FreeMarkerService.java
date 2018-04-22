/**
 * 
 */
package com.zoom.risk.platform.engine.service;

import java.util.Map;

import freemarker.template.Configuration;

/**
 * 模板服务类,主要用来替换sql中的参数
 * @author jiangyulin
 *Nov 19, 2015
 */

public interface FreeMarkerService {

	/**
	 * 
	 * @param config
	 */
	public void setConfig(Configuration config);
	
	/**
	 * @param key
	 * @param riskInput
	 * @return
	 */
	public String merge(String key, Map<String,Object> riskInput);
	
}
