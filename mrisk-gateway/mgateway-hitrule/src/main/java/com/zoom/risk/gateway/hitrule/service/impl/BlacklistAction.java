package com.zoom.risk.gateway.hitrule.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.gateway.hitrule.common.HitRuleAnnotation;
import com.zoom.risk.gateway.hitrule.service.HitRuleService;

import java.util.Map;

/**
 * @author jiangyulin
 *May 1, 2016
 */
@HitRuleAnnotation(value="blacklistAction",actionCode="DV0000005")
public class BlacklistAction implements HitRuleService {

    @Override
    public void doAction(Map<String, Object> riskInput) {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(riskInput));
    }
}
