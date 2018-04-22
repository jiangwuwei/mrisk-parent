package com.zoom.risk.operating.roster.vo;

import java.sql.Timestamp;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class Roster {
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

    private Long id;
    private Integer rosterOrigin;
    private Integer rosterType;
    private String content;
    private String creator;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String rosterOriginName;
    private String rosterTypeName;

    public Roster(){

    }

    public Roster(Integer rosterOrigin, Integer rosterType, String content, String creator){
        this.rosterOrigin = rosterOrigin;
        this.rosterType = rosterType;
        this.content = content;
        this.creator = creator;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRosterOrigin() {
        return rosterOrigin;
    }

    public void setRosterOrigin(Integer rosterOrigin) {
        this.rosterOrigin = rosterOrigin;
    }

    public Integer getRosterType() {
        return rosterType;
    }

    public void setRosterType(Integer rosterType) {
        this.rosterType = rosterType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getRosterOriginName() {
        return rosterOriginName;
    }

    public void setRosterOriginName(String rosterOriginName) {
        this.rosterOriginName = rosterOriginName;
    }

    public String getRosterTypeName() {
        return rosterTypeName;
    }

    public void setRosterTypeName(String rosterTypeName) {
        this.rosterTypeName = rosterTypeName;
    }

}
