/**
 * 
 */
package com.zoom.risk.platform.datacenter.service;

import java.util.Map;
import java.util.Set;

import com.zoom.risk.platform.engine.vo.Quota;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
public interface QuotaCollectorService {
	
	/**
	 * @param quotasSet
	 * @param colneRiskInput
	 * @return
	 */
	public Map<String,Object> collectQuotas(Set<Quota> quotasSet,  Map<String,Object> cloneRiskInput);
	
}
