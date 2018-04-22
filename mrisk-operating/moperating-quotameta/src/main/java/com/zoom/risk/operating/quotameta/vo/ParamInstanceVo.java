package com.zoom.risk.operating.quotameta.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jiangyulin on 2015/5/25.
 */
public class ParamInstanceVo implements Serializable {
    private String chineseName;
    private String name;
    private Integer dataType;
    private String defaultValue;
    private Integer mandatory;
    private String description;

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
}
