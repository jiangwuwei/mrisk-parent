package com.zoom.risk.platform.config.utils;

/**
 * @author jiangyulin
 * @version 2.0
 * @date 2015/2/18
 */
public interface EventConstant {
    public static final String  EVENT_ANTIFRAUND = "antifraud";
    public static final String  EVENT_DTREE = "dtree";
    public static final String  EVENT_SCARD = "scard";
    public static final String  EVENT_ROSTERCONFIG = "rosterconfig";
    public static final String  EVENT_JADE_SCENES = "jadescene";

    public static final String[] configs = new String[]{EVENT_ANTIFRAUND,EVENT_DTREE,EVENT_SCARD,EVENT_ROSTERCONFIG,EVENT_JADE_SCENES};
}
