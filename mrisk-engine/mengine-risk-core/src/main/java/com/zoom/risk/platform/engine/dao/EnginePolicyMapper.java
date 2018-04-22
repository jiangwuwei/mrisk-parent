/**
 * 
 */
package com.zoom.risk.platform.engine.dao;

import java.util.List;

import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import com.zoom.risk.platform.engine.vo.Policy;
import com.zoom.risk.platform.engine.vo.PolicyRouter;
import com.zoom.risk.platform.engine.vo.PolicySet;
import com.zoom.risk.platform.engine.vo.Quota;
import com.zoom.risk.platform.engine.vo.Rule;
import com.zoom.risk.platform.engine.vo.RuleQuotaLink;

/**
 * @author jiangyulin
 *Nov 16, 2015
 */
@ZoomiBatisRepository(value="enginePolicyMapper")
public interface EnginePolicyMapper {
	
	public List<PolicySet> findPolicySet();
	
	public List<PolicyRouter> findPolicyRouter();
	
	public List<Policy> findPolicy();
	
	public List<Quota> findQuota();
	
	public List<Rule> findRule();
	
	public List<RuleQuotaLink> findLink();
	
}