package com.zoom.risk.gateway.extend;

import com.zoom.risk.gateway.annotation.ContextExtendedAnnotation;
import com.zoom.risk.gateway.fraud.utils.RiskConstant;
import com.zoom.risk.gateway.fraud.utils.RiskResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Sep 23, 2015
 */
@ContextExtendedAnnotation(value = "initDataExtension", order = 1, includes = {RiskResult.RISK_BUSI_TYPE_ANTIFRAUD})
public class InitDataExtension implements ContextExtension {
    private static final Map<String,Object> extendMap = new HashMap<>();

    static{
        extendMap.put(RiskConstant.DEVICE_FINGERPRINT, null);
        extendMap.put(RiskConstant.DEVICE_FPIP, null);
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
