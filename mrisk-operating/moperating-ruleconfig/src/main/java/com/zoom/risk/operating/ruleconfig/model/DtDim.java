package com.zoom.risk.operating.ruleconfig.model;

/**
 * decision tree dim model
 */
public class DtDim {
	public static final String QUOTA_SOURCE_TYPE = "QuotaSourceType";
	public static final String REFUSE_REASON = "RefuseReason";
	public static final String ACTION_CODE = "actionCode";
	public static final String DV = "DV"; //alias for action code
    public static final String FAIL_REASON = "failReason"; //alias for refuse reason

    private String name;
    
    private String code;

    private String id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}