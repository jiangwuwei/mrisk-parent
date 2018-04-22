package com.zoom.risk.operating.ruleconfig.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jiangyulin on 2017/5/25.
 */
public class DtParamTemplate implements Serializable {
    private Long id;
    private String chineseName;
    private String name;
    private Integer dataType;
    private String defaultValue;
    private Integer mandatory;
    private String description;
    private Date createdDate;
    private Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DtParamTemplate{");
        sb.append("id=").append(id);
        sb.append(", chineseName='").append(chineseName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", dataType=").append(dataType);
        sb.append(", defaultValue='").append(defaultValue).append('\'');
        sb.append(", mandatory=").append(mandatory);
        sb.append(", description='").append(description).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", modifiedDate=").append(modifiedDate);
        sb.append('}');
        return sb.toString();
    }
}
