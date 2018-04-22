package com.zoom.risk.platform.decision.vo;

import java.io.Serializable;

/**
 * Created by jiangyulin on 2017/5/19.
 */
public class QutoaInfo implements Serializable {
    private Long quotaId;
    private String paramName;
    private String value;
    private Long takingTime;

    public QutoaInfo(){

    }


    public QutoaInfo(Long quotaId, String paramName, String value, Long takingTime){
        this.quotaId = quotaId;
        this.paramName = paramName;
        this.value = value;
        this.takingTime = takingTime;
    }


    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTakingTime() {
        return takingTime;
    }

    public void setTakingTime(Long takingTime) {
        this.takingTime = takingTime;
    }
}
