package com.zoom.risk.operating.ruleconfig.model;

import com.zoom.risk.operating.ruleconfig.common.Constants;

public class ExtendAttributeDim {

    private String chineseName;

    private Byte dataType;

    private Integer id;

    private String paramName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    public Byte getDataType() {
        return dataType;
    }

    public void setDataType(Byte dataType) {
        this.dataType = dataType;
    }

    //字段属性名称
    public String getDataTypeName(){
        if(dataType.intValue() == 1){
            return Constants.DATA_TYPE_NUMBER;
        }
        return Constants.DATA_TYPE_STRING;
    }
}