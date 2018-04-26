/**
 * 
 */
package com.zoom.risk.gateway.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Sep 23, 2015
 */
public class RiskResult {
	//网关事件处理的状态 默认为 0;  0: 成功 1: 数据校验异常 2: 调用风控引擎异常
	public static final String GATEWAY_STATUS_OK = "0";
	public static final String GATEWAY_STATUS_PARAM_ERROR = "1";
	public static final String GATEWAY_STATUS_SYSTEM_ERROR = "2";

	//自研风控引擎执行模式,平行执行或者替代执行
	public static final String SCENE_EXECUTING_MODE_PARALLEL= "1";
	public static final String SCENE_EXECUTING_MODE_INSTEAD= "2";

	//业务回传的业务操作状态,默认是初始状态
	public static final String RISK_STATUS_INIT = "0";
	public static final String RISK_STATUS_SUCCESSFUL = "1";
	public static final String RISK_STATUS_FAILURE = "2";

	//风控决策结果,默认通过
	public static final int RISK_DECISION_CODE_PASS = 1;
	public static final int RISK_DECISION_CODE_AUDIT = 2;
	public static final int RISK_DECISION_CODE_DECLINE = 3;

	//风控事件类型
	public static final String RISK_BUSI_TYPE_ANTIFRAUD = "1";     //反欺诈
	public static final String RISK_BUSI_TYPE_DECISION_TREE = "2"; //决策树
	public static final String RISK_BUSI_TYPE_SCARD = "3"; //评分卡

	//数据都发到统一kafka主题，通过 RISK_TYPE 来入不同的 索引
	public static final String RISK_TYPE_MONITOR = "1";     //risk_monitor 索引
	public static final String RISK_TYPE_EVENT = "2";       //risk_event   事件索引

	public static final String RESULT_DECISION_CODE = "decisionCode";
	public static final String RESULT_ACTION_CODE = "actionCode";


	public static Map<String,Object> getDefaultResult(){
		Map<String,Object> defaultResult = new HashMap<String,Object>();
		defaultResult.put(RESULT_DECISION_CODE, RISK_DECISION_CODE_PASS);
		defaultResult.put(RESULT_ACTION_CODE, null);
		return defaultResult;
	}

}
