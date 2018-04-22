package com.zoom.risk.platform.scard.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class ScoreCardResponse implements Serializable{
	private static final long serialVersionUID = -1795750427434826095L;
	public static final int RESPONSE_OK = 200;              //正确处理返回
	public static final int RESPONSE_NOT_FOUND = 404;       //无策略集或者规则错误
	public static final int RESPONSE_SERVER_ERROR = 500;    //系统错误
	public static final int RESPONSE_SERVER_TIMEOUT = 502;  //处理超时错误
	
	private int responseCode;
	private String responseDesc;
	private Float engineScardScore;
	private String engineScardRuleFinal;

	private Map<String,Object> extendedData;

	
	public ScoreCardResponse(int responseCode, String responseDesc){
		this();
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}
	
	public ScoreCardResponse(){
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

	public Float getEngineScardScore() {
		return engineScardScore;
	}

	public void setEngineScardScore(Float engineScardScore) {
		this.engineScardScore = engineScardScore;
	}

	public String getEngineScardRuleFinal() {
		return engineScardRuleFinal;
	}

	public void setEngineScardRuleFinal(String engineScardRuleFinal) {
		this.engineScardRuleFinal = engineScardRuleFinal;
	}

	public Map<String, Object> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(Map<String, Object> extendedData) {
		this.extendedData = extendedData;
	}

	public Object getExtendedValue(String key){
		return extendedData.get(key);
	}
}
