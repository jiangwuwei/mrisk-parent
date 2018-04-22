package com.zoom.risk.operating.decision.po;

import com.zoom.risk.operating.decision.vo.ParamInstanceVo;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangyulin on 2015/5/16.
 */
public class TQuota {
    public static final  int SOURCE_TYPE_1 = 1;  //请求上下文
    public static final  int SOURCE_TYPE_2 = 2;  //内部的服务

    public static final int DATA_TYPE_1 = 1;  //数字类型
    public static final int DATA_TYPE_2 = 2;  //字符串
    public static final int DATA_TYPE_3 = 3;  //boolean数字类型

    public static final int STATUS_1 = 1;  //拟草
    public static final int STATUS_2 = 2;  //生效

    private Long id;
    private Long templateId;
    private String sceneNo;
    private String quotaNo;
    private String chineseName;
    private String paramName;
    private Integer sourceType;
    private Integer dataType;
    private String requestParams;
    private Integer status;
    private String description;
    private Date createdDate;
    private Date modifiedDate;

    private List<ParamInstanceVo> paramsList;

    public List<ParamInstanceVo> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<ParamInstanceVo> paramsList) {
        this.paramsList = paramsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public String getQuotaNo() {
        return quotaNo;
    }

    public void setQuotaNo(String quotaNo) {
        this.quotaNo = quotaNo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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



    public static TQuota convert(TQuotaTemplate template, String sceneNo){
        TQuota quota = new TQuota();
        quota.setDataType(template.getDataType());
        quota.setSourceType(template.getSourceType());
        quota.setDescription(template.getDescription());
        quota.setParamName(template.getParamName());
        quota.setChineseName(template.getChineseName());
        quota.setRequestParams(template.getRequestParams());
        quota.setTemplateId(template.getId());
        quota.setStatus(TQuota.STATUS_2);
        quota.setSceneNo(sceneNo);
        return quota;
    }


}
