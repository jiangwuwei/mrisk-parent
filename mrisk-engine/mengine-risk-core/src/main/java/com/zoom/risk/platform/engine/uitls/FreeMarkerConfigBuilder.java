/**
 * 
 */
package com.zoom.risk.platform.engine.uitls;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
public class FreeMarkerConfigBuilder {
	private Configuration config;
	private StringTemplateLoader stringTemplateLoader;
	
	public FreeMarkerConfigBuilder(){
		config = new Configuration(Configuration.VERSION_2_3_23);  
		stringTemplateLoader = new StringTemplateLoader();
	}
	
	public FreeMarkerConfigBuilder addTemplate(String key, String template){
		stringTemplateLoader.putTemplate(key, template);
		return this;
	}
	
	public Configuration create(){
		config.setTemplateLoader(stringTemplateLoader);
		return config;
	}
}
