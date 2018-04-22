package com.zoom.risk.platform.decision.po;


import com.zoom.risk.platform.decision.vo.RouteMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public class TRule {
    private Long id;
    private Long policyId;
    private String sceneNo;
    private String name;
    private String ruleContent;
    private List<RouteMode> routes;
    private Integer ruleLayer;
    private String ruleParams;
    private String ruleNo;
    private Integer status;
    private Integer score;
    private Integer decisionCode;
    private String actionCode;
    private String reason;
    private String description;

    public TRule(){
    }

    public TRule(Long policyId, String sceneNo){
        this.policyId = policyId;
        this.sceneNo = sceneNo;
    }

    public TRule(Long policyId, String sceneNo, Integer ruleLayer, String ruleParams){
        this.policyId = policyId;
        this.sceneNo = sceneNo;
        this.ruleLayer = ruleLayer;
        this.ruleParams = ruleParams;
    }

    public TRule(Long policyId, String sceneNo, Integer ruleLayer, String ruleParams, Integer decisionCode, String actionCode, String reason){
        this(policyId,sceneNo,ruleLayer,ruleParams);
        this.decisionCode = decisionCode;
        this.actionCode = actionCode;
        this.reason = reason;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public Integer getRuleLayer() {
        return ruleLayer;
    }

    public void setRuleLayer(Integer ruleLayer) {
        this.ruleLayer = ruleLayer;
    }

    public String getRuleParams() {
        return ruleParams;
    }

    public void setRuleParams(String ruleParams) {
        this.ruleParams = ruleParams;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RouteMode> getRoutes() {
        return routes;
    }

    public void addRoutes(RouteMode routeMode) {
        if ( routes == null ){
            routes = new ArrayList<>();
        }
        this.routes.add(routeMode);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
