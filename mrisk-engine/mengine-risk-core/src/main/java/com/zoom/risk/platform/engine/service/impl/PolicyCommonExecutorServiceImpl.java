package com.zoom.risk.platform.engine.service.impl;

import com.zoom.risk.platform.engine.mode.EnginePolicy;
import com.zoom.risk.platform.engine.service.PolicyCommonExecutorService;
import com.zoom.risk.platform.engine.vo.Policy;
import com.zoom.risk.platform.engine.vo.Quota;
import com.zoom.risk.platform.engine.vo.Rule;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service("policyCommonExecutorService")
public class PolicyCommonExecutorServiceImpl implements PolicyCommonExecutorService {

    @Override
    public EnginePolicy getCommonPolicy(String scene4no) {
        EnginePolicy enginePolicy = new EnginePolicy();
        enginePolicy.setPolicy(this.getPolicy(scene4no));
        Set<Rule> rules = this.getRule(scene4no);
        rules.forEach((rule)->{ enginePolicy.addRule(rule);});
        Set<Quota> quotas = this.getQuotas(scene4no);
        quotas.forEach((quota)->{enginePolicy.addQuota(quota);});
        return enginePolicy;
    }

    private Set<Rule> getRule(String scene4no){
        Set<Rule> set = new HashSet<Rule>();
        Rule rule = new Rule();
        rule.setId(0L);
        rule.setPolicyId(0L);
        rule.setRuleNo("R0000_0000000");
        rule.setName("黑名单规则");
        rule.setRuleContent( "( extendHitBlackList == 1 )" );
        rule.setScore(800f);
        rule.setStatus(Rule.RULE_STATUS_EFFECTIVE_2);
        rule.setSceneNo(scene4no);
        ExpressionCompiler compiler = new ExpressionCompiler(rule.getRuleContent());
        CompiledExpression exp = compiler.compile();
        rule.setCompiledExpression(exp);
        set.add(rule);
        return set;
    }

    private Set<Quota> getQuotas(String scene4no){
        return Collections.emptySet();
    }

    private Policy getPolicy(String scene4no){
        Policy policy = new Policy();
        policy.setPolicyNo("P" + scene4no + "_0000000");
        policy.setSceneNo(scene4no);
        policy.setPolicyPattern(Policy.POLICY_PATTERN_SCORE_2);
        policy.setWeightValue(1);
        policy.setWeightGrade("[300,600]");
        policy.setMax(600);
        policy.setMin(300);
        return policy;
    }
}
