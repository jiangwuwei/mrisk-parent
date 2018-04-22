/**
 * 
 */
package com.zoom.risk.platform.engine.api;

import java.io.Serializable;
import java.util.*;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class DecisionResponse implements Serializable{ 
	private static final long serialVersionUID = -1795750427434826095L;
	public static final int RESPONSE_OK = 200;              //正确处理返回
	public static final int RESPONSE_NOT_FOUND = 404;       //无策略集或者规则错误
	public static final int RESPONSE_SERVER_ERROR = 500;    //系统错误
	public static final int RESPONSE_SERVER_TIMEOUT = 502;  //处理超时错误
	
	private int responseCode;
	private String responseDesc;
	private DecisionPolicy hitPolicy;
	private List<DecisionRule> hitRules;
	private Map<String,Object> extendedData;
	
	public DecisionResponse(int responseCode, String responseDesc){
		this();
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}
	
	public DecisionResponse(){
		responseCode = RESPONSE_OK;
		hitRules = new ArrayList<DecisionRule>();
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

	public DecisionPolicy getHitPolicy() {
		return hitPolicy;
	}

	public void setHitPolicy(DecisionPolicy hitPolicy) {
		this.hitPolicy = hitPolicy;
	}

	public List<DecisionRule> getHitRules() {
		return hitRules;
	}

	public void addHitRules(DecisionRule hitRule) {
		this.hitRules.add(hitRule);
	}

	public static DecisionResponse get404Error(String responseDesc){
		return new DecisionResponse(RESPONSE_NOT_FOUND, responseDesc);
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
}
