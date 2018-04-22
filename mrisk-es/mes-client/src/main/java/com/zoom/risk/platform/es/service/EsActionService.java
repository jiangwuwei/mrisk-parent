package com.zoom.risk.platform.es.service;

/**
 * @author jiangyulin
 *Oct 31, 2015
 */
public interface EsActionService {
	/**
	 * How do we know a doc is to be created or updated ?
	 * According to riskStatus as we know the value of riskStatus is equal to 0 then create one
	 * or else update the doc 
	 * @param jsonSource
	 * @return
	 */
	public boolean dispatchEvent(String jsonSource);

}
