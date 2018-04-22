package com.zoom.risk.platform.scard.mode;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class SCardParamRouteVo {
    private Long id;
    private Long paramId;
    private String paramName;
    private String routeName;
    private String routeExpression;
    private Float routeScore;

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

    public Float getRouteScore() {
        return routeScore;
    }

    public void setRouteScore(Float routeScore) {
        this.routeScore = routeScore;
    }

    @Override
    public String toString() {
        return "SCardParamRouteVo{" +
                "id=" + id +
                ", paramId=" + paramId +
                ", paramName='" + paramName + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeExpression='" + routeExpression + '\'' +
                ", routeScore=" + routeScore +
                '}';
    }
}