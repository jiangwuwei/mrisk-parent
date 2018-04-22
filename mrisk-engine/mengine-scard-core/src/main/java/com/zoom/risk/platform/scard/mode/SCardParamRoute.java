package com.zoom.risk.platform.scard.mode;

import org.mvel2.compiler.CompiledExpression;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class SCardParamRoute {
    private Long id;
    private Long paramId;
    private String paramName;
    private String routeName;
    private String routeExpression;
    private CompiledExpression compiledExpression;
    private String routeScore;
    private Integer dbType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteExpression() {
        return routeExpression;
    }

    public void setRouteExpression(String routeExpression) {
        this.routeExpression = routeExpression;
    }

    public String getRouteScore() {
        return routeScore;
    }

    public void setRouteScore(String routeScore) {
        this.routeScore = routeScore;
    }

    public Integer getDbType() {
        return dbType;
    }

    public void setDbType(Integer dbType) {
        this.dbType = dbType;
    }

    public CompiledExpression getCompiledExpression() {
        return compiledExpression;
    }

    public void setCompiledExpression(CompiledExpression compiledExpression) {
        this.compiledExpression = compiledExpression;
    }

    @Override
    public String toString() {
        return "ScoreCardParamRoute{" +
                "id=" + id +
                ", paramId=" + paramId +
                ", paramName='" + paramName + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeExpression='" + routeExpression + '\'' +
                ", compiledExpression=" + compiledExpression +
                ", routeScore=" + routeScore +
                ", dbType=" + dbType +
                '}';
    }
}