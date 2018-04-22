package com.zoom.risk.operating.ruleconfig.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mvel2.compiler.CompiledExpression;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
	private Long id;

	public static final int DECISION_CODE_PASS_0 = 0;
	public static final int DECISION_CODE_AUIT_1 = 1;
	public static final int DECISION_CODE_DECLINE_2 = 2;
	public static final Pattern QUOTA_PATTERN = Pattern.compile(Quota.QUOTA_REGULAR,
			Pattern.CASE_INSENSITIVE);

	private Long policyId;
	private String ruleContent;
	private String ruleContentJson;
	private Integer status; // '规则状态 1:拟草 2:生效 3: 废弃',
	private String sceneNo;
	private BigDecimal score;
	private Integer decisionCode; // 操作结果 只有关联的策略的 policy_pattern == 1 是才有效 1
									// 人工审核 2 拒绝 只有这两种
	private String actionCode; // 指令操作 只有关联的策略的 policy_pattern == 1 是才有效, 自定义
	private CompiledExpression compiledExpression;
	private String name;
	private Integer weightValue;
	private String ruleNo;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDate;
	private Date modifiedDate;
	private Integer ruleMode; //规则模式， 0-简单模式， 1-复杂模式

	//指标list
	private List<String> quotaNoList;

	public List<String> getQuotaNoList() {
		return quotaNoList;
	}

	public void setQuotaNoList(List<String> quotaNoList) {
		this.quotaNoList = quotaNoList;
	}

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
		this.ruleContent = ruleContent == null ? null : ruleContent.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
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
		this.sceneNo = sceneNo == null ? null : sceneNo.trim();
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
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
		this.actionCode = actionCode == null ? null : actionCode.trim();
	}

	public Integer getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(Integer weightValue) {
		this.weightValue = weightValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public CompiledExpression getCompiledExpression() {
		return compiledExpression;
	}

	public void setCompiledExpression(CompiledExpression compiledExpression) {
		this.compiledExpression = compiledExpression;
	}

	// 方便前端展示
	public String getCreatedDateStr() {
		return DateFormatUtils.format(createdDate, "yyyy-MM-dd HH:mm:ss");
	}
	
	//状态-中文
	public String getChStatus(){
		return Constants.STATUS_ARR[status];
	}
	
	public String getRuleContentJson() {
		return ruleContentJson;
	}

	public void setRuleContentJson(String ruleContentJson) {
		this.ruleContentJson = ruleContentJson;
	}

	public List<String> getQuotas(){
		Matcher matcher = QUOTA_PATTERN.matcher(ruleContent);
		Set<String> quotaNoSet = Sets.newHashSet();
		while (matcher.find()) {
			quotaNoSet.add(matcher.group().trim());
		}
		List<String> quotaNoList = Lists.newArrayList();
		quotaNoList.addAll(quotaNoSet);
		return quotaNoList;
	}
	
	public RuleCondition getRuleCondition(){
		if(StringUtils.isBlank(ruleContentJson)){
			return new RuleCondition();
		}
		return new Gson().fromJson(ruleContentJson, RuleCondition.class);
	}

	public Integer getRuleMode() {
		return ruleMode;
	}

	public void setRuleMode(Integer ruleMode) {
		this.ruleMode = ruleMode;
	}

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", policyId=" + policyId +
                ", ruleContent='" + ruleContent + '\'' +
                ", ruleContentJson='" + ruleContentJson + '\'' +
                ", status=" + status +
                ", sceneNo='" + sceneNo + '\'' +
                ", score=" + score +
                ", decisionCode=" + decisionCode +
                ", actionCode='" + actionCode + '\'' +
                ", compiledExpression=" + compiledExpression +
                ", name='" + name + '\'' +
                ", weightValue=" + weightValue +
                ", ruleNo='" + ruleNo + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", ruleMode=" + ruleMode +
                ", quotaNoList=" + quotaNoList +
                '}';
    }
}