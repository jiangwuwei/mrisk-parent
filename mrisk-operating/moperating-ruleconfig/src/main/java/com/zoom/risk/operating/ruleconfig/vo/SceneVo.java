package com.zoom.risk.operating.ruleconfig.vo;

public class SceneVo {

	private String engName;
	private String chName;
	private String value;

	public SceneVo(String engName, String chName){
		this.chName = chName;
		this.engName = engName;
	}

	public SceneVo(String engName, String chName, String value){
		this.chName = chName;
		this.engName = engName;
		this.value = value;
	}
	
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
