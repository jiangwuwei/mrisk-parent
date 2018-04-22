/**
 * 
 */
package com.zoom.risk.platform.engine.service;

import com.zoom.risk.platform.engine.mode.EnginePolicyWrapper;
import com.zoom.risk.platform.engine.vo.Quota;

import java.util.Map;

/**
 * @author jiangyulin
 *Nov 12, 2015
 */
public interface PolicyCacheService {
	
	/** 
	 * @param sceneNo
	 * @param riskInput
	 * @return
	 */
	public EnginePolicyWrapper getPolicy(String sceneNo, Map<String,Object> riskInput);

	public Map<String,Quota> getQuotaMap();
	
	public void refresh();
	
}