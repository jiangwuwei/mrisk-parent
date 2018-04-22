/**
 * 
 */
package com.zoom.risk.platform.engine.mode;

import java.util.List;

import com.zoom.risk.platform.engine.vo.Policy;
import com.zoom.risk.platform.engine.vo.PolicyRouter;
import com.zoom.risk.platform.engine.vo.PolicySet;
import com.zoom.risk.platform.engine.vo.Quota;
import com.zoom.risk.platform.engine.vo.Rule;
import com.zoom.risk.platform.engine.vo.RuleQuotaLink;

/**Represent all valid data in database
 * @author jiangyulin
 *Nov 16, 2015
 */
public class EngineDatabase {
	private List<PolicySet> policySet;
	private List<PolicyRouter> policyRouter;
	private List<Policy> policy;
	private List<Rule> rules;
	private List<RuleQuotaLink> rulesQuotaLinks;
	private List<Quota> quotas;	

	public List<PolicySet> getPolicySet() {
		return policySet;
	}
	public void setPolicySet(List<PolicySet> policySet) {
		this.policySet = policySet;
	}
	public List<PolicyRouter> getPolicyRouter() {
		return policyRouter;
	}
	public void setPolicyRouter(List<PolicyRouter> policyRouter) {
		this.policyRouter = policyRouter;
	}
	public List<Policy> getPolicy() {
		return policy;
	}
	public void setPolicy(List<Policy> policy) {
		this.policy = policy;
	}
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	public List<Quota> getQuotas() {
		return quotas;
	}
	public void setQuotas(List<Quota> quotas) {
		this.quotas = quotas;
	}
	public List<RuleQuotaLink> getRulesQuotaLinks() {
		return rulesQuotaLinks;
	}
	public void setRulesQuotaLinks(List<RuleQuotaLink> rulesQuotaLinks) {
		this.rulesQuotaLinks = rulesQuotaLinks;
	}
	
}
