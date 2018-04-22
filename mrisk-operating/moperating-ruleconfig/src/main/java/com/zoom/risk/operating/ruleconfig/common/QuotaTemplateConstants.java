package com.zoom.risk.operating.ruleconfig.common;

/**
 * Created by liyi8 on 2017/2/22.
 */
public class QuotaTemplateConstants {
    public static final String COMPUTE_TYPE_COUNT = "1"; //出现次数
    public static final String COMPUTE_TYPE_AOSSIATE = "2"; //关联次数
    public static final String COMPUTE_TYPE_DISTINCT = "3"; //去重

    public static final String TABLE_PREFIX = "zoom_risk_scene_";

    public static final int TIME_SHARD_RECENTLY = 1; //时间片，近。。
    public static final int TIME_SHARD_CURR = 2; //时间片，当。。

    public static final String TEMPLATE_TYPE_NUMBER = "1";
    public static final String TEMPLATE_TYPE_STRING = "2";
    public static final String TEMPLATE_TYPE_DATE = "3";
    public static final String TEMPLATE_TYPE_STRING_LIST = "4";

    public static final String TEMPLATE_FUNCTION_STRING_LIST = "StringList";
}
