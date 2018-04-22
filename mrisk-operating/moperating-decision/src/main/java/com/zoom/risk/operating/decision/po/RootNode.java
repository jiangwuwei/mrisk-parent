package com.zoom.risk.operating.decision.po;

/**
 * Created by jiangyulin on 2015/5/10.
 */
public class RootNode extends Node {
    protected String chineseName;
    protected String paramName;
    protected Long quotaId;

    public RootNode(){
        this.nodeType = NODE_TYPE_ROOT;
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
}
