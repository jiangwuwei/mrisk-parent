/**
 * 
 */
package com.zoom.risk.gateway.extend.service;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface RiskExtendFramework {

	Map<String, Object> decorate(Map<String, Object> riskData, String riskBusiTYpe);

}
