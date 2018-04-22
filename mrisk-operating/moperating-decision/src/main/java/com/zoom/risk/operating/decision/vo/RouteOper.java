package com.zoom.risk.operating.decision.vo;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public interface RouteOper {

    public static final String SPACER = " ";

    public static final String  GT = ">";
    public static final String  GET = ">=";
    public static final String  LT = "<";
    public static final String  LET = "<=";
    public static final String  EQ = "==";


    public static final int PARAM_DATA_TYPE_DIGITAL = 1;   //数字类型
    public static final int PARAM_DATA_TYPE_STRING = 2;    //字符串类型
    public static final int PARAM_DATA_TYPE_B_DIGITAL = 3;    //boolean数字

    public String getMevlExpression();

}
