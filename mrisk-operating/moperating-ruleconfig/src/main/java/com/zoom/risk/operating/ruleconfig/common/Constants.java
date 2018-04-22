package com.zoom.risk.operating.ruleconfig.common;

public class Constants {

	//策略，规则，指标状态
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_IN_EFFECT = 2;
	public static final int STATUS_DISCARD = 3;
	public static final int STATUS_IMITATE= 4;
	public static final int STATUS_DISCARD_TO_INEFFECT = 10;
	public static final String[] STATUS_ARR = {"","拟草","生效","废弃","模拟"}; 
	
	//策略模式
	public static int POLICY_PATTERN_WORSE = 1;  //1: 最坏模式
	public static int POLICY_PATTERN_SCORE = 2;  //2:权重模式
	public static final String[] POLICY_PATTERN_ARR = {"","最坏模式","权重模式"};

    public static final String RISK_DATE = "riskDate";
	public static final String MVEL_TOOLS_COND_FORMAT = "%s %s %s";
	public static final String MVEL_TOOLS_TIME = "com.zoom.RuleTools.getTime(%s)";
	public static final String MVEL_TOOLS_EMPTY = "%s == empty";
	public static final String MVEL_TOOLS_CONTAINS = "%s contains(%s)";
	public static final String MVEL_TOOLS_NOT_CONTAINS = "!%s contains(%s)";
	public static final String MVEL_TOOLS_LIST_CONTAINS = "%s.contains(%s)";
	public static final String MVEL_TOOLS_LIST_NOT_CONTAINS = "!%s.contains(%s)";

	public static final String SQL_IS_EMPTY = "%s = '' || %s is NULL";
	public static final String SQL_CONTAINS = "%s like '%s'";
	public static final String SQL_NOT_CONTAINS = "%s not like '%s'";

	public static final String[] CERTIFICATE_CH_ARRAY={"","身份证","护照","回乡证","台胞证","军官证","警官证","士兵证"};
	public static final String[] CERTIFICATE_ENG_ARRAY={"","IT01","IT02","IT03","IT04","IT05","IT06","IT07"};

	public static final String DATA_TYPE_NUMBER = "数字";
	public static final String DATA_TYPE_STRING = "字符";
	public static final String DATA_TYPE_STRINGLIST = "字符List";
	public static final String DATA_TYPE_MONEY = "金额";
	public static final String DATA_TYPE_DATE = "日期";

	public static final String DATA_SOUCE_DATA_SER = "4";

	public static final int DECISION_CODE_PASS = 1;
	public static final int DECISION_CODE_VERIFY = 2;
	public static final int DECISION_CODE_REFUSE = 3;

}
