package com.zoom.risk.operating.decision.po;

import java.math.BigDecimal;

/**
 * Created by jiangyulin on 2015/5/10.
 */
public class BranchNode extends Node {
    protected String chineseName;
    protected String paramName;
    protected Long quotaId;

    private String routeName;
    private String routeExpression;
    private BigDecimal score;

    public BranchNode(){
        this.nodeType = NODE_TYPE_BRANCH;
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

    public Long getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(Long quotaId) {
        this.quotaId = quotaId;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
