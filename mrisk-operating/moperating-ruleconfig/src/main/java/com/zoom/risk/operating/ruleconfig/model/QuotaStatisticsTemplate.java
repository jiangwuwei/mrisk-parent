package com.zoom.risk.operating.ruleconfig.model;

import com.zoom.risk.operating.ruleconfig.common.Condition;

import java.util.List;

/**
 * Created by liyi8 on 2017/2/22.
 * 指标统计模板
 */
public class QuotaStatisticsTemplate {
    private String function; //计算函数
    private int timeShardType; //时间片类型
    private int timeShardValue; //时间片值
    private String timeShardUnit; //时间片单位
    private String primaryAttr; //主属性
    private String secondAttr; //从属性
    private String computeType; //计算类型
    private String computeField; //计算字段
    private boolean isAnd; //过滤拼接逻辑
    private String orderField;
    private String order;
    private String limit;
    private String offset;
    private List<Condition>  conditions;//过滤条件

    public int getTimeShardType() {
        return timeShardType;
    }

    public void setTimeShardType(int timeShardType) {
        this.timeShardType = timeShardType;
    }

    public int getTimeShardValue() {
        return timeShardValue;
    }

    public void setTimeShardValue(int timeShardValue) {
        this.timeShardValue = timeShardValue;
    }

    public String getTimeShardUnit() {
        return timeShardUnit;
    }

    public void setTimeShardUnit(String timeShardUnit) {
        this.timeShardUnit = timeShardUnit;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public boolean getIsAnd() {
        return isAnd;
    }

    public void setIsAnd(boolean isAnd) {
        this.isAnd = isAnd;
    }

    public String getPrimaryAttr() {
        return primaryAttr;
    }

    public void setPrimaryAttr(String primaryAttr) {
        this.primaryAttr = primaryAttr;
    }

    public String getSecondAttr() {
        return secondAttr;
    }

    public void setSecondAttr(String secondAttr) {
        this.secondAttr = secondAttr;
    }

    public String getComputeType() {
        return computeType;
    }

    public void setComputeType(String computeType) {
        this.computeType = computeType;
    }

    public String getComputeField() {
        return computeField;
    }

    public void setComputeField(String computeField) {
        this.computeField = computeField;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }


    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "QuotaStatisticsTemplate{" +
                "function='" + function + '\'' +
                ", timeShardType=" + timeShardType +
                ", timeShardValue=" + timeShardValue +
                ", timeShardUnit='" + timeShardUnit + '\'' +
                ", primaryAttr='" + primaryAttr + '\'' +
                ", secondAttr='" + secondAttr + '\'' +
                ", computeType='" + computeType + '\'' +
                ", computeField='" + computeField + '\'' +
                ", isAnd=" + isAnd +
                ", conditions=" + conditions +
                '}';
    }
}