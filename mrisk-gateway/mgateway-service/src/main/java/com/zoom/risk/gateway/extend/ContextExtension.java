/**
 * 
 */
package com.zoom.risk.gateway.extend;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface ContextExtension {

	public Map<String, Object> invoke(Map<String, Object> riskData);

}
