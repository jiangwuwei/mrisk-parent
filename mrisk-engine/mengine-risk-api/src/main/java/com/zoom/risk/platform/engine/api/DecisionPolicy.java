/**
 * 
 */
package com.zoom.risk.platform.engine.api;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class DecisionPolicy implements Serializable {
	private static final long serialVersionUID = 2353200077814689007L;
	public static int POLICY_PATTERN_WORSE_1 = 1;  //1: 最坏模式
	public static int POLICY_PATTERN_SCORE_2 = 2;  //2:权重模式

	private Long id;
	private String name;                  //策略名称 
	private String sceneNo;               //场景号4位
	private String policyNo;              //策略编号
	private Integer policyPattern;        //策略模式 1: 最坏模式 2:权重模式
	private Float finalValue;             //权重模式的最终分数
	private Integer finalDecisionCode;    //最终结果 1:通过 2:人工审核 3:拒绝
	private String weightGrade;           //权值分档  比如 [300,500]
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
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
	public Integer getPolicyPattern() {
		return policyPattern;
	}
	public void setPolicyPattern(Integer policyPattern) {
		this.policyPattern = policyPattern;
	}
	public Float getFinalValue() {
		return finalValue;
	}
	public void setFinalValue(Float finalValue) {
		this.finalValue = finalValue;
	}
	public Integer getFinalDecisionCode() {
		return finalDecisionCode;
	}
	public void setFinalDecisionCode(Integer finalDecisionCode) {
		this.finalDecisionCode = finalDecisionCode;
	}

	public String getWeightGrade() {
		return weightGrade;
	}

	public void setWeightGrade(String weightGrade) {
		this.weightGrade = weightGrade;
	}
}
