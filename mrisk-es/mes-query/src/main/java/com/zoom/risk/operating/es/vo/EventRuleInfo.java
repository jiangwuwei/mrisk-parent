package com.zoom.risk.operating.es.vo;

import java.util.ArrayList;
import java.util.List;

public class EventRuleInfo {
	private List<RuleInfo> ruleInfos;
	private long eventTotalCount;
	private String sceneNo;
	
	public EventRuleInfo(long eventTotalCount){
		this();
		this.eventTotalCount = eventTotalCount;
	}
	
	public EventRuleInfo(){
		ruleInfos = new ArrayList<RuleInfo>();
	}

	public List<RuleInfo> getRuleInfos() {
		return ruleInfos;
	}
	
	public void add(RuleInfo ruleInfo){
		ruleInfos.add(ruleInfo);
	}
	
	public long getEventTotalCount() {
		return eventTotalCount;
	}

	public void setEventTotalCount(long eventTotalCount) {
		this.eventTotalCount = eventTotalCount;
	}

	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("sceneNo:" + sceneNo);
		builder.append(" , eventTotalCount:" + eventTotalCount);
		if ( ruleInfos.isEmpty()){
			builder.append(", ruleInfo:[]");
		}else{
			builder.append(", ruleInfo:[");
			ruleInfos.forEach((ruleInfo)->{
				builder.append(ruleInfo+",");
			});
			builder.append("]");
		}
		return builder.toString();
	}
	
	
	public static class RuleInfo{
		private String ruleNo;
		private long hitCount;
		
		public RuleInfo(){
		}
		
		public RuleInfo(String ruleNo, long hitCount){
			this.ruleNo = ruleNo;
			this.hitCount = hitCount;
		}
		
		public String getRuleNo() {
			return ruleNo;
		}
		public void setRuleNo(String ruleNo) {
			this.ruleNo = ruleNo;
		}
		public long getHitCount() {
			return hitCount;
		}
		public void setHitCount(long hitCount) {
			this.hitCount = hitCount;
		}
		
		@Override
		public String toString() {
			return "ruleNo:" + ruleNo +", hitCount:" +hitCount;
		}
		
	}
	

}
