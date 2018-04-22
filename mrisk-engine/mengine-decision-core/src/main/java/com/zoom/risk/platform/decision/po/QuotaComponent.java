package com.zoom.risk.platform.decision.po;

import java.io.Serializable;

/**
 * Created by jiangyulin on 2017/5/24.
 */
public class QuotaComponent implements Serializable {
    private Long id;
    private String name;
    private Integer dataType;
    private String defaultValue;
    private Integer mandatory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
