package com.zoom.risk.platform.kafka.consumer.vo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiangyulin on 2015/3/7.
 */
public class UpdateMode {
    private int retryTimes;
    private String riskId;
    private String serialNumber;
    private String jsonSource;

    public UpdateMode(String riskId, String serialNumber, String jsonSource){
        this.retryTimes = 0;
        this.riskId = riskId;
        this.serialNumber = serialNumber;
        this.jsonSource = jsonSource;
    }

    public UpdateMode(String riskId, String serialNumber, String jsonSource, int retryTimes){
        this.retryTimes = retryTimes;
        this.riskId = riskId;
        this.serialNumber = serialNumber;
        this.jsonSource = jsonSource;
    }

    public String getRiskId() {
        return riskId;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getJsonSource() {
        return jsonSource;
    }
}
