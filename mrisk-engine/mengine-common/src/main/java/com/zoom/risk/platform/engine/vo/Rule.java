/**
 * 
 */
package com.zoom.risk.platform.engine.vo;

import org.mvel2.compiler.CompiledExpression;

/**
 * @author jiangyulin Nov 12, 2015
 */
public class Rule extends AbstractBase {
	public static final int DECISION_CODE_PASS_1 = 1;     //通过
	public static final int DECISION_CODE_AUDIT_2 = 2;     //人工审核
	public static final int DECISION_CODE_DECLINE_3 = 3;  //拒绝

	public static final int RULE_STATUS_DRAFT_1 = 1;
	public static final int RULE_STATUS_EFFECTIVE_2 = 2;
	public static final int RULE_STATUS_ABANDON_3 = 3;
	public static final int RULE_STATUS_SIMULATE_4 = 4;

	private Long policyId;
	private String ruleContent;
	private String ruleNo;
	private Integer status; // '规则状态 1:拟草 2:生效 3: 废弃 4: 模拟',
	private String sceneNo;
	private Float score;
	private Integer decisionCode; // 操作结果 只有关联的策略的 policy_pattern == 1 是才有效 1 人工审核 2 拒绝 只有这两种
	private String actionCode; // 指令操作 只有关联的策略的 policy_pattern == 1 是才有效, 自定义
	private String executeOrder; // 排序
	private CompiledExpression compiledExpression;

	
	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getRuleContent() {
		return ruleContent;
	}

	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
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

	public String getExecuteOrder() {
		return executeOrder;
	}

	public void setExecuteOrder(String executeOrder) {
		this.executeOrder = executeOrder;
	}
	
	public CompiledExpression getCompiledExpression() {
		return compiledExpression;
	}

	public void setCompiledExpression(CompiledExpression compiledExpression) {
		this.compiledExpression = compiledExpression;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{id=" + this.id);
		builder.append(",policyId=" + this.policyId);
		builder.append(",name=" + this.name);
		builder.append(",score=" + this.score);
		builder.append(",sceneNo=" + this.sceneNo);
		builder.append(",ruleContent=" + this.ruleContent);
		builder.append(",decisionCode=" + this.decisionCode);
		builder.append(",actionCode=" + this.actionCode);
		builder.append("}");
		return builder.toString();
	}
 
}
