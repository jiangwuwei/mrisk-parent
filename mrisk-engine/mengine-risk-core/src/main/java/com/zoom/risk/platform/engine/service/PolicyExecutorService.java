package com.zoom.risk.platform.engine.service;

import com.zoom.risk.platform.engine.api.DecisionPolicyRouter;
import com.zoom.risk.platform.engine.api.DecisionResponse;
import com.zoom.risk.platform.engine.vo.Policy;
import com.zoom.risk.platform.engine.vo.PolicyRouter;
import com.zoom.risk.platform.engine.vo.Rule;

import java.util.Map;
import java.util.Set;

/**
 * Created by jiangyulin on 2015/12/5.
 */
public interface PolicyExecutorService {

    public int execute(Map<String, Object> mvelMap, Set<Rule> rules, Policy policy, Set<Rule> hitRules );

    public void convert2Response(Policy policy, Set<Rule> rules, DecisionResponse response);

    public DecisionPolicyRouter convertPolicyRouter(PolicyRouter policyRouter);
}
