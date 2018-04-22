package com.zoom.risk.operating.decision.po;

import java.util.Date;
import java.util.List;

public class TPolicies {
    private String sceneNo;

    private String name;

    private String description;

    private Date createdDate;

    private Date modifiedDate;

    private List<TQuota> quotasList;

    public List<TQuota> getQuotasList() {
        return quotasList;
    }

    public void setQuotasList(List<TQuota> quotasList) {
        this.quotasList = quotasList;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}