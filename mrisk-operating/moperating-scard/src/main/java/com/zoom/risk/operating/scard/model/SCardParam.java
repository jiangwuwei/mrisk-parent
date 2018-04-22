package com.zoom.risk.operating.scard.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class SCardParam {
    private Long id;
    private Long scardId;
    private Integer typeId;
    private String paramNo;
    private String chineseName;
    private String paramName;
    private Integer dbType;
    private Float defaultScore;
    private Float weightValue;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private List<SCardParamRoute> routeList;

    public List<SCardParamRoute> getRouteList() {
        return routeList;
    }

    public void addRoute(SCardParamRoute route) {
        if ( routeList == null ) {
            routeList = new ArrayList<>();
        }
        routeList.add(route);
    }

    public void setRouteList(List<SCardParamRoute> routeList) {
        this.routeList = routeList;
    }

    public String getParamNo() {
        return paramNo;
    }

    public void setParamNo(String paramNo) {
        this.paramNo = paramNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScardId() {
        return scardId;
    }

    public void setScardId(Long scardId) {
        this.scardId = scardId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public Integer getDbType() {
        return dbType;
    }

    public void setDbType(Integer dbType) {
        this.dbType = dbType;
    }

    public Float getDefaultScore() {
        return defaultScore;
    }

    public void setDefaultScore(Float defaultScore) {
        this.defaultScore = defaultScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Float weightValue) {
        this.weightValue = weightValue;
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