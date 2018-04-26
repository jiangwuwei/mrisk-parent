package com.zoom.risk.gateway.extend.service.impl;

import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.gateway.extend.common.ExtendedAnnotation;
import com.zoom.risk.gateway.extend.service.ContextExtension;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Sep 23, 2016
 */
@ExtendedAnnotation(value = "initDataExtension", order = 1, includes = {RiskResult.RISK_BUSI_TYPE_ANTIFRAUD})
public class InitDataExtension implements ContextExtension {
    public static final String DEVICE_FINGERPRINT = "deviceFingerprint";
    public static final String DEVICE_FPIP = "deviceFpip";

    private static final Map<String,Object> extendMap = new HashMap<>();

    static{
        extendMap.put(DEVICE_FINGERPRINT, null);
        extendMap.put(DEVICE_FPIP, null);
        extendMap.put("extendHitBlackList", 0);
        extendMap.put("extendUseProxy", 0);
    }

    @Override
    public Map<String, Object> invoke(Map<String, Object> riskData) {
        extendMap.forEach((key, value) -> {
            if (!riskData.containsKey(key)) {
                riskData.put(key, value);
            }
        });
        return riskData;
    }
}
