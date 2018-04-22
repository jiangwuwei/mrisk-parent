package com.zoom.risk.operating.decision.vo;

import java.util.List;

/**
 * Created by jiangyulin on 2017/5/16.
 */
public class QuotaTemplateVo {
    private Long id;
    private Integer sourceId;
    private String sourceName;
    private String chineseName;
    private String paramName;
    private Integer sourceType;
    private Integer dataType;
    private String requestParams;
    private String description;

    private List<ParamInstanceVo> paramList;

    public List<ParamInstanceVo> getParamList() {
        return paramList;
    }

    public void setParamList(List<ParamInstanceVo> paramList) {
        this.paramList = paramList;
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

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
