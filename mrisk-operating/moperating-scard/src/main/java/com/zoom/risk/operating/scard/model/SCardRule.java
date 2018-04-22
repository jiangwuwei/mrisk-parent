package com.zoom.risk.operating.scard.model;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class SCardRule {
    private Long id;
    private Long scardId;
    private String routeName;
    private String routeExpression;
    private String finalResult;

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

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }
}