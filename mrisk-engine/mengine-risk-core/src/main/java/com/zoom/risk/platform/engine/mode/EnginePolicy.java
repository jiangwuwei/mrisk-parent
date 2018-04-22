/**
 * 
 */
package com.zoom.risk.platform.engine.mode;

import java.util.HashSet;
import java.util.Set;

import com.zoom.risk.platform.engine.vo.Policy;
import com.zoom.risk.platform.engine.vo.Quota;
import com.zoom.risk.platform.engine.vo.Rule;

/**Represent a policy
 * @author jiangyulin
 *Nov 16, 2015
 */
public class EnginePolicy {
	private Policy policy;
	private Set<Rule> rules;
	private Set<Quota> quotas;
	
	public EnginePolicy(){
		rules = new HashSet<Rule>();
		quotas = new HashSet<Quota>();
	}
	
	public Policy getPolicy() {
		return policy;
	}
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	public Set<Rule> getRules() {
		return rules;
	}
 
	public Set<Quota> getQuotas() {
		return quotas;
	}
 
	public void addRule(Rule rule){
		this.rules.add(rule);
	}
	
	public void addQuota(Quota quota){
		this.quotas.add(quota);
	}
}
