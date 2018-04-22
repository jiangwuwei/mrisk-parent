/**
 * 
 */
package com.zoom.risk.platform.decision.cache;

import com.zoom.risk.platform.decision.po.DecisionTreeWrapper;
import com.zoom.risk.platform.decision.po.TQuota;

/**
 * @author jiangyulin
 *Nov 12, 2015
 */
public interface DTreeCacheService {

	public DecisionTreeWrapper getPolicyDecisionTree(String sceneNo);

	public TQuota getQuota(String sceneNo, String paramName);
	
	public void refresh();
	
}