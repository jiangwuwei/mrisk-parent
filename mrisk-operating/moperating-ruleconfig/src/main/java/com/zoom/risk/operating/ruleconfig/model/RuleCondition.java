package com.zoom.risk.operating.ruleconfig.model;

import com.zoom.risk.operating.ruleconfig.common.Condition;

import java.util.List;

/**
 * 用于表示json格式的规则内容
 * @author liyi8
 *
 * 2015年12月13日
 */
public class RuleCondition {

	private boolean isAnyTime; //是否有时间限制
	private String minTime; //规则运行时间 >
	private String maxTime;//规则运行时间 <=
	private String timeOper; //规则时间条件的连接操作
	private boolean isAnd; //拼接条件
	private List<Condition> conditions;
	private List<Long> quotaIdList;
	
	public boolean getIsAnyTime(){
		return isAnyTime;
	}
	public void setIsAnyTime(boolean isAnyTime){
		this.isAnyTime = isAnyTime;
	}
	public boolean getIsAnd(){
		return isAnd;
	}
	public void setIsAnd(boolean isAnd){
		this.isAnd = isAnd;
	}
	public String getMinTime() {
		return minTime;
	}
	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}
	public String getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}
	public String getTimeOper() {
		return timeOper;
	}
	public void setTimeOper(String timeOper) {
		this.timeOper = timeOper;
	}
	public List<Condition> getConditions() {
		return conditions;
	}
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	public List<Long> getQuotaIdList() {
		return quotaIdList;
	}
	public void setQuotaIdList(List<Long> quotaIdList) {
		this.quotaIdList = quotaIdList;
	}
	
}
