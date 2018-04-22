package com.zoom.risk.platform.scard.mode;

import java.util.*;

/**
 * @author jiangyulin
 *May 22, 2015
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

    public SCardParam(){
        routeSet = new HashSet<>();
    }

    private Set<SCardParamRoute> routeSet;

    public Set<SCardParamRoute> getRouteSet() {
        return routeSet;
    }

    public void addRoute(SCardParamRoute route) {
        routeSet.add(route);
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

    public Float getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Float weightValue) {
        this.weightValue = weightValue;
    }

    @Override
    public String toString() {
        return "SCardParam{" +
                "id=" + id +
                ", scardId=" + scardId +
                ", typeId=" + typeId +
                ", paramNo='" + paramNo + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", paramName='" + paramName + '\'' +
                ", dbType=" + dbType +
                ", defaultScore=" + defaultScore +
                ", weightValue=" + weightValue +
                ", routeSet=" + routeSet +
                '}';
    }
}
