package com.zoom.risk.operating.quotameta.po;

import com.zoom.risk.operating.quotameta.vo.ParamInstanceVo;

import java.io.Serializable;

/**
 * Created by jiangyulin on 2015/5/25.
 */
public class ParamInstance implements Serializable {
    private Long id;
    private Long quotaId;
    private Long templateId;
    private String chineseName;
    private String name;
    private Integer dataType;
    private String defaultValue;
    private Integer mandatory;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(Long quotaId) {
        this.quotaId = quotaId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getMandatory() {
        return mandatory;
    }

    public void setMandatory(Integer mandatory) {
        this.mandatory = mandatory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public ParamInstanceVo copyVo(){
        ParamInstanceVo vo = new ParamInstanceVo();
        vo.setChineseName(this.chineseName);
        vo.setDataType(this.dataType);
        vo.setDefaultValue(this.defaultValue);
        vo.setDescription(this.description);
        vo.setMandatory(this.mandatory);
        vo.setName(this.name);
        return vo;
    }
}
