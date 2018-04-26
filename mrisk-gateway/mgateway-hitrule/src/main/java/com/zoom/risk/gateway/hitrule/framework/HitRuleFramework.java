package com.zoom.risk.gateway.hitrule.framework;

import com.zoom.risk.gateway.hitrule.service.HitRuleService;

import java.util.Map;
import java.util.Set;

/**
 * @author jiangyulin
 *May 1, 2016
 */
public interface HitRuleFramework {

    void publishEvent(Set<String> actionCodes, Map<String,Object> riskInput);

    Map<String,HitRuleService> getHitRuleActions();
}
