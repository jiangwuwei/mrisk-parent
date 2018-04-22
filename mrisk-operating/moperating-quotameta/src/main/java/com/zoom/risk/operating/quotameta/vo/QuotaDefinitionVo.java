package com.zoom.risk.operating.quotameta.vo;

import com.zoom.risk.operating.quotameta.po.ParamInstance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangyulin on 2015/5/25.
 */
public class QuotaDefinitionVo implements Serializable {
    private Long id;
    private Integer sourceId;
    private String chineseName;
    private String paramName;
    private Integer dataType;
    private String requestParams;
    private Integer sycStatus;
    private String description;
    private Date createdDate;
    private Date modifiedDate;

    private String sourceName;

    private List<ParamInstance> paramsList;

    public List<ParamInstance> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<ParamInstance> paramsList) {
        this.paramsList = paramsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getSycStatus() {
        return sycStatus;
    }

    public void setSycStatus(Integer sycStatus) {
        this.sycStatus = sycStatus;
    }
}
