/**
 * 
 */
package com.zoom.risk.gateway.service;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface RiskExtendService {
	public Map<String, Object> decorate(Map<String, Object> riskData, String riskBusiTYpe);
}
