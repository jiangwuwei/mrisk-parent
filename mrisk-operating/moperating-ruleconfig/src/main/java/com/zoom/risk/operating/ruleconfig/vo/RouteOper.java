package com.zoom.risk.operating.ruleconfig.vo;

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
    public static final String  CONTAINS = "contains";
    public static final String  NOT_CONTAINS = "notContains";
    public static final String  IS_EMPTY = "isEmpty";

    public String getMevlExpression();

}
