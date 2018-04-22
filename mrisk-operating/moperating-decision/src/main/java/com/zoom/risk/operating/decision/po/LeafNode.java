package com.zoom.risk.operating.decision.po;

/**
 * Created by jiangyulin on 2015/5/10.
 */
public class LeafNode extends Node {
    private String routeName;
    private String routeExpression;
    private Integer score;

    private Integer decisionCode;
    private String actionCode;
    private String reason;
    private String descrpiton;


    public LeafNode(){
        this.nodeType = NODE_TYPE_LEAF;
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

    public String getDescrpiton() {
        return descrpiton;
    }

    public void setDescrpiton(String descrpiton) {
        this.descrpiton = descrpiton;
    }
}
