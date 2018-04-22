package com.zoom.risk.platform.scard.mode;

import org.mvel2.compiler.CompiledExpression;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class SCardRouter {
    private Long id;
    private String sceneNo;//场景号4位
    private Long scardId;
    private String name;
    private String routerExpression;
    private CompiledExpression compiledExpression;
    private String description;
    private Integer weightValue;
    private String routerNo;
    private int status;

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
        this.sceneNo = sceneNo;
    }

    public Long getScardId() {
        return scardId;
    }

    public void setScardId(Long scardId) {
        this.scardId = scardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouterExpression() {
        return routerExpression;
    }

    public void setRouterExpression(String routerExpression) {
        this.routerExpression = routerExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Integer weightValue) {
        this.weightValue = weightValue;
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

    public CompiledExpression getCompiledExpression() {
        return compiledExpression;
    }

    public void setCompiledExpression(CompiledExpression compiledExpression) {
        this.compiledExpression = compiledExpression;
    }

    @Override
    public String toString() {
        return "ScoreCardRouter{" +
                "id=" + id +
                ", sceneNo='" + sceneNo + '\'' +
                ", scardId=" + scardId +
                ", name='" + name + '\'' +
                ", routerExpression='" + routerExpression + '\'' +
                ", compiledExpression=" + compiledExpression +
                ", description='" + description + '\'' +
                ", weightValue=" + weightValue +
                ", routerNo='" + routerNo + '\'' +
                ", status=" + status +
                '}';
    }
}