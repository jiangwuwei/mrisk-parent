package com.zoom.risk.gateway.decision.utils;


/**
 * 风控统一网关常用的常量
 * @author jiangyulin
 */
public interface RiskDTreeConstant {
	public static final String RISK_SCENE = "scene";
	public static final String RISK_UID= "uid";
	public static final String RISK_TOKEN = "token";
	public static final String RISK_SOURCE_IP = "sourceIp";
	public static final String RISK_PLATFORM = "platform";

	public static final String RISK_ID = "riskId";
	public static final String RISK_DATE = "riskDate";
	public static final String RISK_STATUS = "riskStatus";      // 0 报送  1 成功 2 失败
	
 	
	public static final String PLATFORM_WEB = "WEB";
	public static final String PLATFORM_IOS = "IOS";
	public static final String PLATFORM_ANDROID = "ANDROID";
	
	public static final String RESULT_DECISION_CODE = "decisionCode";
	public static final String RESULT_ACTION_CODE = "actionCode";
	public static final String RESULT_RULES_CODE = "rulesCode";

}

