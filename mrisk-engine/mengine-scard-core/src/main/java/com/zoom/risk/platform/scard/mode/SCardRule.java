package com.zoom.risk.platform.scard.mode;

import org.mvel2.compiler.CompiledExpression;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class SCardRule {
    private Long id;
    private Long scardId;
    private String routeName;
    private String routeExpression;
    private CompiledExpression compiledExpression;
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

    public CompiledExpression getCompiledExpression() {
        return compiledExpression;
    }

    public void setCompiledExpression(CompiledExpression compiledExpression) {
        this.compiledExpression = compiledExpression;
    }

    @Override
    public String toString() {
        return "SCardRule{" +
                "id=" + id +
                ", scardId=" + scardId +
                ", routeName='" + routeName + '\'' +
                ", routeExpression='" + routeExpression + '\'' +
                ", compiledExpression=" + compiledExpression +
                ", finalResult='" + finalResult + '\'' +
                '}';
    }
}