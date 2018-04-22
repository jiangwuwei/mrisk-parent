package com.zoom.risk.platform.decision.po;


import com.zoom.risk.platform.decision.vo.DBNodeVo;
import com.zoom.risk.platform.decision.vo.RouteMode;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public class DBNode extends Node implements Serializable {
    protected String chineseName;
    protected String paramName;
    protected Long quotaId;

    private String routeName;
    private String routeExpression;
    private Integer score;

    private Integer decisionCode;
    private String actionCode;
    private String reason;

    private RouteMode routeMode;

    //以上数据认为是原始数据，下面的数据是 业务处理的数据

    private List<DBNode> childrens;  //lazy created

    public void addChild(DBNode child){
        if ( childrens == null ){
            childrens = new ArrayList<>();
        }
        childrens.add(child);
    }

    @Transient
    public List<DBNode> getChildrens(){
        return childrens == null ? Collections.emptyList() : childrens;
    }

    public void clearChildren(){
        childrens = null;
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

    public RouteMode getRouteMode() {
        return routeMode;
    }

    public void setRouteMode(RouteMode routeMode) {
        this.routeMode = routeMode;
    }

    public DBNodeVo copyOne(){
        DBNodeVo node = new DBNodeVo();
        node.setId(this.id);
        node.setParentId(this.parentId);
        node.setPolicyId(this.policyId);
        node.setSceneNo(this.sceneNo);
        node.setNodeType(this.nodeType);
        node.setActionCode(this.actionCode) ;
        node.setDecisionCode(this.decisionCode);
        node.setRouteMode(this.routeMode);
        node.setChineseName(this.chineseName);
        node.setParamName(this.paramName);
        node.setQuotaId(this.quotaId);
        node.setNodeNo(this.nodeNo);
        node.setRouteName(this.routeName);
        node.setScore(this.score);
        node.setReason(this.reason);
        return node;
    }
}
