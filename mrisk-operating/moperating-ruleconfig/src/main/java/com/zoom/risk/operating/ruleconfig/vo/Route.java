/**
 *
 */
package com.zoom.risk.operating.ruleconfig.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoom.risk.operating.ruleconfig.common.SceneConstants;

import static com.zoom.risk.operating.ruleconfig.common.Constants.*;

/**
 * @author jiangyulin May 9, 2017
 */
public class Route implements RouteOper {

    private String paramName;
    private int paramDataType;
    private String operation;
    private String value;

    public Route() {
    }

    public Route(String paramName, int paramDataType, String operation, String value) {
        this.paramName = paramName;
        this.paramDataType = paramDataType;
        this.operation = operation;
        this.value = value;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public int getParamDataType() {
        return paramDataType;
    }

    public void setParamDataType(int paramDataType) {
        this.paramDataType = paramDataType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JSONField(serialize = false)
    public String getMevlExpression() {
        String expr;
        switch (this.operation) {
            case IS_EMPTY:
                expr = String.format(MVEL_TOOLS_EMPTY, this.paramName);
                break;
            case CONTAINS:
                expr = String.format(MVEL_TOOLS_CONTAINS, this.paramName, this.formatValue());
                break;
            case NOT_CONTAINS:
                expr = String.format(MVEL_TOOLS_NOT_CONTAINS, this.paramName, this.formatValue());
                break;
            default:
                expr = String.format(MVEL_TOOLS_COND_FORMAT, this.paramName, this.operation, this.formatValue());
        }
        return expr;
    }

    private String formatValue() {
        if (this.paramDataType == SceneConstants.DB_TYPE_INT || this.paramDataType == SceneConstants.DB_TYPE_BIGINT || this.paramDataType == SceneConstants.DB_TYPE_DECIMAL) {
            return this.value;
        }
        return String.format("'%s'", this.value);
    }
}
