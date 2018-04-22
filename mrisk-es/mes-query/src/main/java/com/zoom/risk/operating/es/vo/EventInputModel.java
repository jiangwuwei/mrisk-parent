package com.zoom.risk.operating.es.vo;

import java.util.HashMap;
import java.util.Map;

public class EventInputModel{
	private Map<String,String> extendMap;
	private String sceneNo;
	private String startRiskDate;
	private String endRiskDate;
	private String uid;
	private String decisionCode;
	private String platform;
	private String ruleNo;
	private int pageSize;
	private int currentPage;

	public EventInputModel(){
		extendMap = new HashMap<>();
	}
	
	public String getSceneNo() {
		return sceneNo;
	}
	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}
	public String getStartRiskDate() {
		return startRiskDate;
	}
	public void setStartRiskDate(String startRiskDate) {
		this.startRiskDate = startRiskDate;
	}
	public String getEndRiskDate() {
		return endRiskDate;
	}
	public void setEndRiskDate(String endRiskDate) {
		this.endRiskDate = endRiskDate;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDecisionCode() {
		return decisionCode;
	}
	public void setDecisionCode(String decisionCode) {
		this.decisionCode = decisionCode;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public Map<String, String> getExtendMap() {
		return extendMap;
	}

	public void put(String key, String value){
		extendMap.put(key,value);
	}
}
