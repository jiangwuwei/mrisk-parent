package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.zoom.risk.operating.ruleconfig.common.Condition;
import com.zoom.risk.operating.ruleconfig.common.ConditionUtils;
import com.zoom.risk.operating.ruleconfig.model.Quota;
import com.zoom.risk.operating.ruleconfig.model.Rule;
import com.zoom.risk.operating.ruleconfig.model.RuleCondition;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

@Service("ruleConditionService")
public class RuleConditionService {

	/**
	 * 将json格式的ruleContent转换成风控引擎能用的规则内容
	 * @return
	 */
	public void formatRuleContent(Rule rule, RuleCondition ruleCondition){
		StringBuilder content = new StringBuilder("( ");
		//时间段条件
		getTimeContent(rule, content, ruleCondition);
		//值条件
		Set<String> quotaNoSet = Sets.newHashSet();
		content = getOtherContent(rule, content, ruleCondition, quotaNoSet);
		
		rule.setRuleContent(content.toString());
		List<String> quotaNoList = Lists.newArrayList();
		quotaNoList.addAll(quotaNoSet);
		rule.setQuotaNoList(quotaNoList);
		rule.setRuleContentJson(new Gson().toJson(ruleCondition));
	}

	/**
	 * 获取时间段之外的条件
	 * @param rule
	 * @param content
	 * @param ruleCondition
	 */
	public StringBuilder getOtherContent(Rule rule, StringBuilder content, RuleCondition ruleCondition,Set<String> quotaNoSet){
		if(ruleCondition.getConditions() != null){
			if(!ruleCondition.getIsAnyTime()){
				content.append(" && ");
				content.append("( ");
			}
			//解析拼接条件
			ListIterator<Condition> listIterator = ruleCondition.getConditions().listIterator();
			while(listIterator.hasNext()){
				Condition condition = listIterator.next();
				if(condition.getAttr() == null){
					listIterator.remove();
					continue;
				}
				ConditionUtils.analysisMvl(content,condition);
		 		//条件连接符号
				if(ruleCondition.getIsAnd()){
					content.append(" && ");
				}else{
					content.append(" || ");
				}
		 		//用于插入规则指标连接表
				if(condition.getAttr().matches(Quota.QUOTA_REGULAR)){
					quotaNoSet.add(condition.getAttr());
				}
			}
			//消除最后的条件连接符号
			content = new StringBuilder(content.subSequence(0, content.length()-4));
			content.append(" )");
			
			if(!ruleCondition.getIsAnyTime()){
				content.append(" )");
			}
		}else{//去掉开头的括号
			content = new StringBuilder(content.subSequence(1,content.length()));
		}
		
		return content;
	}
	
	/**
	 * 规则时间段解析
	 * @param rule
	 * @param content
	 * @param ruleCondition
	 */
	public void getTimeContent(Rule rule, StringBuilder content, RuleCondition ruleCondition){
		if(!ruleCondition.getIsAnyTime()){
			content.append("( ");
			//拼接minTime
			content.append(String.format(Constants.MVEL_TOOLS_TIME, Constants.RISK_DATE));
			content.append(" >= ");
			content.append(ruleCondition.getMinTime());
			//拼接maxTime
			content.append(" ");
			content.append(ruleCondition.getTimeOper());
			content.append(" ");
			content.append(String.format(Constants.MVEL_TOOLS_TIME, Constants.RISK_DATE));
			content.append(" < ");
			content.append(ruleCondition.getMaxTime());
			content.append(" )");
		}
	}
	
	/**
	 * 根据json字符串格式的规则内容获取指标编号
	 * @param ruleConditionJson
	 * @return
	 */
	public List<String> getQuotaNoList(String ruleConditionJson){
		RuleCondition ruleCondition = new Gson().fromJson(ruleConditionJson, RuleCondition.class);
		Set<String> quotaNoSet = Sets.newHashSet();
		for (Condition condition : ruleCondition.getConditions()) {
			if(condition.getAttr().matches(Quota.QUOTA_REGULAR)){
				quotaNoSet.add(condition.getAttr());
			}
		}
		List<String> quotaNoList = Lists.newArrayList();
		quotaNoList.addAll(quotaNoSet);
		return quotaNoList;
	}
}
