package com.zoom.risk.platform.sharding.dbrouter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This JadeDsKeyHolder is used to identify use jade to select datasource
 * @author jiangyulin
 *
 */
public class DsKeyHolder {
	private static final Logger logger = LogManager.getLogger(DsKeyHolder.class);
	private final ThreadLocal<String> dsHolder = new ThreadLocal<>();
	private String defaultValue;
	
	public String getDsKeyStr(){
		return dsHolder.get();
	}
	
	public void setDsKeyStr(String dsKey){
		dsHolder.set(dsKey);
	}
	
	public void cleanupDsKey(){
		dsHolder.remove();
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
}
