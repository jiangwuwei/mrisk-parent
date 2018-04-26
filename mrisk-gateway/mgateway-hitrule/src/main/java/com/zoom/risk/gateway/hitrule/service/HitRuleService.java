package com.zoom.risk.gateway.hitrule.service;

import java.util.Map;

/**
 * @author jiangyulin
 *May 1, 2018
 */
public interface HitRuleService {

    void doAction(Map<String,Object> riskInput);

}
