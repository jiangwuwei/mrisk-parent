/**
 * 
 */
package com.zoom.risk.platform.engine.vo;

/**
 * @author jiangyulin Nov 12, 2015
 */
public class Policy extends AbstractBase {
	public static int POLICY_PATTERN_WORSE_1 = 1;  //1: 最坏模式
	public static int POLICY_PATTERN_SCORE_2 = 2;  //2:权重模式
	
	private String sceneNo;                //场景号4位
	private String policyNo;
	private Integer policyPattern;        //策略模式 1: 最坏模式 2:权重模式
	private Integer weightValue;            //权重取值, 只有policy_pattern == 2 是才有效   min,max 形式
	private Float finalValue;             //权重模式的最终分数
	private Integer finalDecisionCode;    //最终结果
	private String weightGrade;           //权值分档  比如 300,500
	private Integer min;
	private Integer max;
	
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

	public Integer getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(Integer weightValue) {
		this.weightValue = weightValue;
	}

	public String getWeightGrade() {
		return weightGrade;
	}

	public void setWeightGrade(String weightGrade) {
		this.weightGrade = weightGrade;
	}

	public Float getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(Float finalValue) {
		this.finalValue = finalValue;
	}
	
	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
	
	public Integer getFinalDecisionCode() {
		return finalDecisionCode;
	}

	public void setFinalDecisionCode(Integer finalDecisionCode) {
		this.finalDecisionCode = finalDecisionCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{id=" + this.id);
		builder.append(",name=" + this.name);
		builder.append(",sceneNo=" + this.sceneNo);
		builder.append(",policyPattern=" + this.policyPattern);
		builder.append(",weightValue=" + this.weightValue);
		builder.append(",weightGrade=" + this.weightGrade);
		builder.append(",finalValue=" + this.finalValue);
		builder.append(",finalDecisionCode=" + this.finalDecisionCode);
		builder.append("}");
		return builder.toString();
	}
}
