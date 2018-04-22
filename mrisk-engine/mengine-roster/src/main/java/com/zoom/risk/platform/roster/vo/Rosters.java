package com.zoom.risk.platform.roster.vo;

import java.io.Serializable;

/**
 * Created by jiangyulin on 2017/4/5.
 */
public class Rosters implements Serializable{
    public static final String BLACK_KEY = "extendHitBlackList";
    public static final String BLACK_BUSI_TYPE_KEY = "extendBlackBusiType";
    public static final int  ROSTER_BLACKLIST = 1;

    //名单库业务类型  100反欺诈名单库  200 信用名单库 300 营销名单库
    public static final int ROSTER_BUSI_TYPE_ANTIFRAUD = 100;
    public static final int ROSTER_BUSI_TYPE_CREDIT = 200;
    public static final int ROSTER_BUSI_TYPE_AD = 300;

    //名单内容种类 uid 手机号码 身份证号码以及 银行卡号码
    public static final String ROSTER_UID = "uid";
    public static final String ROSTER_MOBILE = "mobile";
    public static final String ROSTER_IDNUMBER = "idnumber";
    public static final String ROSTER_CARDNUMBER = "cardnumber";
    public static final String ROSTER_DEVICEFINGERPRINT = "deviceFingerprint";

    //类型标识: 1 uid, 2 mobile,  3 idnumber 4 cardnumber
    public static final int ROSTER_DB_UID = 1;
    public static final int ROSTER_DB_MOBILE = 2;
    public static final int ROSTER_DB_IDNUMBER = 3;
    public static final int ROSTER_DB_CARDNUMBER = 4;
    public static final int ROSTER_DB_DEVICEFINGERPRINT = 5;


    private Integer type;     //黑白名单
    private String key;       // uid, mobile, idnumber, cardnumber
    private String dbValue;   // 1,    2,      3,        4
    private String value;

    public Rosters(){
        type = ROSTER_BLACKLIST;
    }

    public Rosters(String dbValue, String value){
        this.type = ROSTER_BLACKLIST;
        this.dbValue = dbValue;
        this.value = value;
    }


    public Rosters(String key, String dbValue, String value){
        this.type = ROSTER_BLACKLIST;
        this.key = key;
        this.dbValue = dbValue;
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDbValue() {
        return dbValue;
    }

    public void setDbValue(String dbValue) {
        this.dbValue = dbValue;
    }

    @Override
    public String toString() {
        return "Rosters{" + "type=" + type + ", key='" + key + ", dbValue='" + dbValue + ", value='" + value + '}';
    }
}
