package com.zoom.risk.operating.roster.vo;

import java.io.Serializable;

/**
 * Created by jiangyulin on 2015/4/11.
 */
public class RosterCredit implements Serializable {
    public static final int OPER_TYPE_FREEZE_1 = 1;
    public static final int OPER_TYPE_UNFREEZE_2 = 2;

    public static final String SOURCE_TYPE_EUI_1 = "1";
    public static final String SOURCE_TYPE_CASH_2 = "2";
    private String uid;
    private int operType;
    private String sourceType;

    public RosterCredit(){
    }

    public RosterCredit(String uid, int operType, String sourceType){
        this.uid = uid;
        this.operType = operType;
        this.sourceType = sourceType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getOperType() {
        return operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
