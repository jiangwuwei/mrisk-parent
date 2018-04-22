/**
 * 
 */
package com.zoom.risk.platform.engine.api;

import java.util.Map;

/**
 * @author jiangyulin
 *Nov 14, 2015
 */
public interface RuleEngineApi {
	
	/**规则引擎
	 * @param riskInput
	 * @return DecisionResponse
	 */
	public DecisionResponse evaluate(Map<String,Object> riskInput);
	
}
