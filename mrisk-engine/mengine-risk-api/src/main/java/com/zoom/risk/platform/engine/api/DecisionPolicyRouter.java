/**
 * 
 */
package com.zoom.risk.platform.engine.api;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class DecisionPolicyRouter implements Serializable {
	private static final long serialVersionUID = 2353200077814689027L;
	private Long id;
	private String sceneNo;             //场景号4位
	private String  name;
	private String routerNo;
	private Long policyId;
	private Integer weightValue;
	private String routerExpression;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSceneNo() {
		return sceneNo;
	}
	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}
	public String getRouterNo() {
		return routerNo;
	}
	public void setRouterNo(String routerNo) {
		this.routerNo = routerNo;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public Integer getWeightValue() {
		return weightValue;
	}
	public void setWeightValue(Integer weightValue) {
		this.weightValue = weightValue;
	}
	public String getRouterExpression() {
		return routerExpression;
	}
	public void setRouterExpression(String routerExpression) {
		this.routerExpression = routerExpression;
	}
	
}
