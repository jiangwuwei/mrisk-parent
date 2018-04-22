package com.zoom.risk.operating.ruleconfig.model;

public class QuotaDim{
	public static final String ACCESS_SOURCE = "AccessSource";
	public static final String QUOTA_DATA_TYPE = "QuotaDataType";
	public static final String SOURCE_TYPE = "SourceType";
	public static final String STATUS_CODE = "Status";
	
    private String name;
    
    private String code;

    private Integer id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}