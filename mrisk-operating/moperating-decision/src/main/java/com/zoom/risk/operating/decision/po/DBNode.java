package com.zoom.risk.operating.decision.po;

import com.zoom.risk.operating.decision.vo.RouteMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiangyulin on 2015/5/10.
 */
public class DBNode extends Node {
    protected String chineseName;
    protected String paramName;
    protected Long quotaId;

    private String nodeNo;
    private String routeName;
    private String routeExpression;
    private Integer score;

    private Integer decisionCode;
    private String actionCode;
    private String reason;
    private String description;

    private RouteMode routeMode;

    //以上数据认为是原始数据，下面的数据是 业务处理的数据

    private List<DBNode> childrens;  //lazy created

    public static final int IS_HIT_NODE_TRUE = 1;
    private Integer isHitNode; //是否命中节点， null/0：未命中 1 ：命中

    public Integer getIsHitNode() {
        return isHitNode;
    }

    public void setIsHitNode(Integer isHitNode) {
        this.isHitNode = isHitNode;
    }

    public void addChild(DBNode child){
        if ( childrens == null ){
            childrens = new ArrayList<>();
        }
        childrens.add(child);
    }

    public List<DBNode> getChildrens(){
        return childrens == null ? Collections.emptyList() : childrens;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDecisionCode() {
        return decisionCode;
    }

    public void setDecisionCode(Integer decisionCode) {
        this.decisionCode = decisionCode;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RouteMode getRouteMode() {
        return routeMode;
    }

    public void setRouteMode(RouteMode routeMode) {
        this.routeMode = routeMode;
    }

    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }
}
