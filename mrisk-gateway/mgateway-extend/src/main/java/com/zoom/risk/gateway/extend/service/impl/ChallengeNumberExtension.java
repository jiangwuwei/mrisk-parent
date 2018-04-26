package com.zoom.risk.gateway.extend.service.impl;

import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.gateway.extend.common.ExtendedAnnotation;
import com.zoom.risk.gateway.extend.service.ContextExtension;

import java.util.Map;
import java.util.Random;

/**
 * @author jiangyulin
 *Sep 23, 2016
 */
@ExtendedAnnotation(value = "challengeNumberExtension",order = 3, includes = {RiskResult.RISK_BUSI_TYPE_ANTIFRAUD,RiskResult.RISK_BUSI_TYPE_DECISION_TREE,RiskResult.RISK_BUSI_TYPE_SCARD})
public class ChallengeNumberExtension implements ContextExtension {
    private static final Random random = new Random();

    @Override
    public Map<String, Object> invoke(Map<String, Object> riskData) {
        riskData.put("extendChallengeNumber",random.nextInt(100));
        return riskData;
    }
}
