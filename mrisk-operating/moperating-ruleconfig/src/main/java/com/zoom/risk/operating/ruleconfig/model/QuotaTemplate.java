package com.zoom.risk.operating.ruleconfig.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QuotaTemplate {
    private Long id;

    private Integer quotaType;

    private Integer quotaDataType; //指标数据类型

    private Integer sourceType;

    private Integer accessSource;

    private String name;

    private String quotaContent;

    private String description;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    private Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(Integer quotaType) {
        this.quotaType = quotaType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getAccessSource() {
        return accessSource;
    }

    public void setAccessSource(Integer accessSource) {
        this.accessSource = accessSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getQuotaContent() {
        return quotaContent;
    }

    public void setQuotaContent(String quotaContent) {
        this.quotaContent = quotaContent == null ? null : quotaContent.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getQuotaDataType() {
        return quotaDataType;
    }

    public void setQuotaDataType(Integer quotaDataType) {
        this.quotaDataType = quotaDataType;
    }
}