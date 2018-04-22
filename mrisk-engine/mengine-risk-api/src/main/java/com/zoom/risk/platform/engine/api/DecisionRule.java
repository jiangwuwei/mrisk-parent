/**
 * 
 */
package com.zoom.risk.platform.engine.api;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class DecisionRule implements Serializable {
	private static final long serialVersionUID = -7630021980444528623L;
	private Long id;
	protected String name;
	private String ruleNo;
	protected Float score ;         
	private Integer decisionCode;   // 操作结果 只有关联的策略的 policy_pattern == 1 是才有效 1 人工审核 2 拒绝 只有这两种
	private String actionCode;      // 指令操作 只有关联的策略的 policy_pattern == 1 是才有效, 自定义
	private String ruleContent;
	
	
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDecisionCode() {
		return decisionCode;
	}
	public void setDecisionCode(Integer decisionCode) {
		this.decisionCode = decisionCode;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getRuleContent() {
		return ruleContent;
	}
	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}
	
}
