package com.zoom.risk.operating.ruleconfig.model;

import com.zoom.risk.operating.ruleconfig.vo.RouteMode;

import java.util.Date;

public class PolicyRouter {
    private Long id;

    private String sceneNo;//场景号4位

    private Long policyId;

    private String name;

    private String routerExpression;

    private String routerExpressionJson;

    private String description;

    private Integer weightValue;

    private String routerNo;

    private int status;

    private Date createdDate;

    private Date modifiedDate;

    private RouteMode routeMode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo == null ? null : sceneNo.trim();
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRouterExpression() {
        return routerExpression;
    }

    public void setRouterExpression(String routerExpression) {
        this.routerExpression = routerExpression == null ? null : routerExpression.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Integer weightValue) {
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

    public RouteMode getRouteMode() {
        return routeMode;
    }

    public void setRouteMode(RouteMode routeMode) {
        this.routeMode = routeMode;
    }

    public String getRouterNo() {
        return routerNo;
    }

    public void setRouterNo(String routerNo) {
        this.routerNo = routerNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRouterExpressionJson() {
        return routerExpressionJson;
    }

    public void setRouterExpressionJson(String routerExpressionJson) {
        this.routerExpressionJson = routerExpressionJson;
    }
}