package com.zoom.risk.platform.decision.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class DTreeResponse implements Serializable{
	private static final long serialVersionUID = -1795750427434826095L;
	public static final int RESPONSE_OK = 200;              //正确处理返回
	public static final int RESPONSE_NOT_FOUND = 404;       //无策略集或者规则错误
	public static final int RESPONSE_SERVER_ERROR = 500;    //系统错误
	public static final int RESPONSE_SERVER_TIMEOUT = 502;  //处理超时错误
	
	private Integer responseCode;
	private String responseDesc;
	private Integer decisionCode;
	private String actionCode;
	private String reason;
	private Integer score;
	private Map<String,Object> extendedData;
	
	public DTreeResponse(int responseCode, String responseDesc){
		this();
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}
	
	public DTreeResponse(){
		decisionCode = 3;
		responseCode = RESPONSE_OK;
		extendedData = new HashMap<String,Object>();
	}
	
	public boolean isOK(){
		return this.responseCode == RESPONSE_OK; 
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDesc() {
		return responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public static DTreeResponse get404Error(String responseDesc){
		return new DTreeResponse(RESPONSE_NOT_FOUND, responseDesc);
	}

	public Map<String, Object> getExtendedData() {
		return extendedData;
	}

	public void put(String key,Object value){
		extendedData.put(key,value);
	}

	public Object getExtendedValue(String key){
		return extendedData.get(key);
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
