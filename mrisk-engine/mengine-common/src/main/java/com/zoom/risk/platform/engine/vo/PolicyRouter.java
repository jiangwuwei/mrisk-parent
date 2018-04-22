/**
 * 
 */
package com.zoom.risk.platform.engine.vo;

import org.mvel2.compiler.CompiledExpression;

/**
 * @author jiangyulin Nov 12, 2015
 */
public class PolicyRouter extends AbstractBase {
	private String sceneNo;             //场景号4位
	private String routerNo;
	private Long policyId;
	private Integer weightValue;
	private String routerExpression;
	private CompiledExpression compiledExpression;

	
	public String getRouterNo() {
		return routerNo;
	}

	public void setRouterNo(String routerNo) {
		this.routerNo = routerNo;
	}

	public Integer getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(Integer weightValue) {
		this.weightValue = weightValue;
	}

	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getRouterExpression() {
		return routerExpression;
	}

	public void setRouterExpression(String routerExpression) {
		this.routerExpression = routerExpression;
	}

	public CompiledExpression getCompiledExpression() {
		return compiledExpression;
	}

	public void setCompiledExpression(CompiledExpression compiledExpression) {
		this.compiledExpression = compiledExpression;
	}

}
