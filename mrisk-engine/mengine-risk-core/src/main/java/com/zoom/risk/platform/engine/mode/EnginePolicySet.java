package com.zoom.risk.platform.engine.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zoom.risk.platform.engine.vo.PolicyRouter;

/**Represent a polices and include all related data 
 * @author jiangyulin
 *Nov 16, 2015
 */
public class EnginePolicySet {
	private List<PolicyRouter> policyRouter;
	private Map<String,EnginePolicy> policyMap;
	
	public EnginePolicySet(){
		policyMap = new ConcurrentHashMap<String,EnginePolicy>();
		policyRouter = new ArrayList<PolicyRouter>();
	}
	
	public List<PolicyRouter> getPolicyRouter() {
		return policyRouter;
	}
	public void addPolicyRouter(PolicyRouter router) {
		policyRouter.add(router);
	}

	public EnginePolicy getPolicy(String policyId){
		return policyMap.get(policyId);
	}
	
	public Map<String, EnginePolicy> getPolicys() {
		return policyMap;
	}

	public void putPolicy(String policyId, EnginePolicy enginePolicy){
		policyMap.put(policyId, enginePolicy);
	}
	
}
