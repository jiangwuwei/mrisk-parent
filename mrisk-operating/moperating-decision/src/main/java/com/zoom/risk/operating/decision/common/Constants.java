package com.zoom.risk.operating.decision.common;

/**
 * Created by liyi8 on 2015/5/24.
 */
public class Constants {

    public static final int STATUS_DRAFT = 1;
    public static final int STATUS_IN_EFFECT = 2;
    public static final int STATUS_DISCARD = 3;
    public static final int STATUS_IMITATE = 4;

    public static final String ENTITY_CLASS_POLICY = "TP";
    public static final String ENTITY_CLASS_RULE = "TR";
    public static final String ENTITY_CLASS_QUOTA = "TQ";
    public static final String ENTITY_CLASS_NODE = "TN";

    public static final int DECISION_CODE_PASS = 1;
    public static final int DECISION_CODE_VERIFY = 2;
    public static final int DECISION_CODE_DECILINE = 3;
}
