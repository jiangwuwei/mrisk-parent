package com.zoom.risk.gateway.hitrule.common;

import org.springframework.context.ApplicationEvent;

import java.util.Map;
import java.util.Set;

/**
 * @author jiangyulin
 *May 1, 2018
 */
public class HitRuleEvent extends ApplicationEvent {
    private Set<String> actionCodes;
    private Map<String,Object> riskInput;

    public HitRuleEvent(Set<String> actionCodes, Map<String,Object> riskInput) {
        super(actionCodes);
        this.actionCodes = actionCodes;
        this.riskInput = riskInput;
    }

    public Set<String> getActionCodes() {
        return actionCodes;
    }

    public Map<String, Object> getRiskInput() {
        return riskInput;
    }

}
