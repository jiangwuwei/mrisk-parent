package com.zoom.risk.gateway.scard.utils;


/**
 * 风控统一网关常用的常量
 * @author jiangyulin
 */
public interface SCardConstant {
	public static final String RISK_SCENE = "scene";
	public static final String RISK_SOURCE_IP = "sourceIp";
	public static final String RISK_PLATFORM = "platform";

	public static final String RISK_ID = "riskId";
	public static final String RISK_DATE = "riskDate";
	public static final String RISK_STATUS = "riskStatus";      // 0 报送  1 成功 2 失败


	public static final String ENGINE_SCARD_SCORE = "scardScore";
	public static final String ENGINE_SCARD_RULE_FINAL = "scardRuleFinal";
}

