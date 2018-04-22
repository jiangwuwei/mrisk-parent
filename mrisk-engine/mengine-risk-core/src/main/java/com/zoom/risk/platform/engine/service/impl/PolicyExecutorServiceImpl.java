package com.zoom.risk.platform.engine.service.impl;

import com.zoom.risk.platform.engine.api.DecisionPolicy;
import com.zoom.risk.platform.engine.api.DecisionPolicyRouter;
import com.zoom.risk.platform.engine.api.DecisionResponse;
import com.zoom.risk.platform.engine.api.DecisionRule;
import com.zoom.risk.platform.engine.mvel.MvelService;
import com.zoom.risk.platform.engine.service.PolicyExecutorService;
import com.zoom.risk.platform.engine.vo.Policy;
import com.zoom.risk.platform.engine.vo.PolicyRouter;
import com.zoom.risk.platform.engine.vo.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * Created by jiangyulin on 2015/12/5.
 */
@Service("policyExecutorService")
public class PolicyExecutorServiceImpl implements PolicyExecutorService {
    private static final Logger logger = LogManager.getLogger(PolicyExecutorServiceImpl.class);

    @Resource(name="mvelService")
    private MvelService mvelService;

    public int execute(Map<String, Object> mvelMap, Set<Rule> rules, Policy policy, Set<Rule> hitRules ){
        int decisionCode = Rule.DECISION_CODE_PASS_1;
        if ( Policy.POLICY_PATTERN_SCORE_2 ==  policy.getPolicyPattern() ){
            decisionCode = this.scorePattern(mvelMap, rules, policy, hitRules);
        }else if ( Policy.POLICY_PATTERN_WORSE_1 ==  policy.getPolicyPattern() ){
            decisionCode = this.worsePattern(mvelMap, rules, policy, hitRules);
        }
        return decisionCode;
    }

    protected int worsePattern(Map<String, Object> mvelMap, Set<Rule> rules, Policy policy, Set<Rule> hitRules ){
        policy.setFinalDecisionCode(Rule.DECISION_CODE_PASS_1);
        for( Rule rule : rules ){
            try{
                if ( mvelService.simpleEvaluate(mvelMap, rule.getCompiledExpression()) ){
                    hitRules.add(rule);
                    Integer decisionCode = rule.getDecisionCode();
                    if ( rule.getStatus().intValue() == 2  && decisionCode > Rule.DECISION_CODE_PASS_1 ) {
                        policy.setFinalDecisionCode(decisionCode);
                        if ( decisionCode == Rule.DECISION_CODE_DECLINE_3 ){
                            break;
                        }
                    }
                }
            }catch(Exception e){
                logger.error("Executing rule happens error, , rule : " + rule, e);
            }
        }
        policy.setFinalValue(0F);
        return policy.getFinalDecisionCode();
    }

    protected int scorePattern(Map<String, Object> mvelMap, Set<Rule> rules, Policy policy, Set<Rule> hitRules ){
        float finalValue = 0f;
        for( Rule rule : rules ){
            try{
                if ( mvelService.simpleEvaluate(mvelMap, rule.getCompiledExpression()) ){
                    hitRules.add(rule);
                    if ( rule.getStatus().intValue() == Rule.RULE_STATUS_EFFECTIVE_2 ) {
                        finalValue = finalValue + rule.getScore().floatValue();
                    }
                }
            }catch(Exception e) {
                logger.error("Executing rule happens error, rule : " + rule, e);
            }
        }
        if ( finalValue <= policy.getMin() ){
            policy.setFinalDecisionCode(Rule.DECISION_CODE_PASS_1);
        }else if ( finalValue <= policy.getMax() ){
            policy.setFinalDecisionCode(Rule.DECISION_CODE_AUDIT_2);
        }else{
            policy.setFinalDecisionCode(Rule.DECISION_CODE_DECLINE_3);
        }
        policy.setFinalValue(finalValue);
        return policy.getFinalDecisionCode();
    }

    public void convert2Response(Policy policy, Set<Rule> rules, DecisionResponse response){
        DecisionPolicy p = new DecisionPolicy();
        p.setId(policy.getId());
        p.setName(policy.getName());
        p.setSceneNo(policy.getSceneNo());
        p.setFinalValue(policy.getFinalValue());
        p.setPolicyNo(policy.getPolicyNo());
        p.setFinalDecisionCode(policy.getFinalDecisionCode());
        p.setPolicyPattern(policy.getPolicyPattern());
        p.setWeightGrade(policy.getWeightGrade());
        response.setHitPolicy(p);
        rules.forEach(
            (rule)->{
                DecisionRule r = new DecisionRule();
                r.setId(rule.getId());
                r.setActionCode(rule.getActionCode());
                r.setDecisionCode(rule.getDecisionCode());
                r.setName(rule.getName());
                r.setScore(rule.getScore());
                r.setRuleNo(rule.getRuleNo());
                r.setRuleContent(rule.getRuleContent());
                response.addHitRules(r);
            }
        );
    }

    public DecisionPolicyRouter convertPolicyRouter(PolicyRouter policyRouter){
        DecisionPolicyRouter p = null;
        if ( policyRouter != null ) {
            p = new DecisionPolicyRouter();
            p.setId(policyRouter.getId());
            p.setName(policyRouter.getName());
            p.setSceneNo(policyRouter.getSceneNo());
            p.setRouterExpression(policyRouter.getRouterExpression());
            p.setWeightValue(policyRouter.getWeightValue());
            p.setRouterNo(policyRouter.getRouterNo());
            p.setPolicyId(policyRouter.getPolicyId());
        }
        return p;
    }


    public void setMvelService(MvelService mvelService) {
        this.mvelService = mvelService;
    }
}
