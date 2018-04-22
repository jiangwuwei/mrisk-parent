/**
 * 
 */
package com.zoom.risk.platform.engine.mode;

import com.zoom.risk.platform.engine.vo.PolicyRouter;

/**
 * @author jiangyulin
 *Nov 30, 2015
 */
public class EnginePolicyWrapper {
	
	private EnginePolicy enginePolicy;
	
	private PolicyRouter policyRouter;
	
	public EnginePolicy getEnginePolicy() {
		return enginePolicy;
	}
	public void setEnginePolicy(EnginePolicy enginePolicy) {
		this.enginePolicy = enginePolicy;
	}
	public PolicyRouter getPolicyRouter() {
		return policyRouter;
	}
	public void setPolicyRouter(PolicyRouter policyRouter) {
		this.policyRouter = policyRouter;
	}
	
}
