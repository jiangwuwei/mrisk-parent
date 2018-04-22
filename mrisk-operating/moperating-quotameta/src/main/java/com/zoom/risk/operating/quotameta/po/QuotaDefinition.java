package com.zoom.risk.operating.quotameta.po;

import java.io.Serializable;

/**
 * Created by jiangyulin on 2015/5/25.
 */
public class QuotaDefinition implements Serializable {
    private Long id;
    private Integer sourceId;
    private String chineseName;
    private String paramName;
    private Integer dataType;
    private String requestParams;
    private Integer sycStatus = 0 ;   //同步状态 0 未同步 1 已同步
    private String description;

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

    public Integer getSycStatus() {
        return sycStatus;
    }

    public void setSycStatus(Integer sycStatus) {
        this.sycStatus = sycStatus;
    }
}
