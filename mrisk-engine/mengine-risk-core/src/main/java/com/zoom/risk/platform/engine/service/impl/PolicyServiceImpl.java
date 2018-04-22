/**
 * 
 */
package com.zoom.risk.platform.engine.service.impl;

import com.zoom.risk.platform.engine.dao.EnginePolicyMapper;
import com.zoom.risk.platform.engine.mode.EngineDatabase;
import com.zoom.risk.platform.engine.mode.EnginePolicy;
import com.zoom.risk.platform.engine.mode.EnginePolicySet;
import com.zoom.risk.platform.engine.service.FreeMarkerService;
import com.zoom.risk.platform.engine.service.PolicyService;
import com.zoom.risk.platform.engine.uitls.FreeMarkerConfigBuilder;
import com.zoom.risk.platform.engine.vo.*;
import com.zoom.risk.platform.engine.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangyulin
 *Nov 16, 2015
 */
@Service("policyService")
public class PolicyServiceImpl implements PolicyService {
	private static final Logger logger = LogManager.getLogger(PolicyServiceImpl.class);
	
	@Resource(name="enginePolicyMapper")
	private EnginePolicyMapper enginePolicyMapper;
	
	@Resource(name="freeMarkerService")
	private FreeMarkerService freeMarkerService;
	
	/**
	 * @return
	 */
	@Transactional(readOnly = true)
	public EngineDatabase findEnginePolicies(){
		EngineDatabase enginePolicies = new EngineDatabase();
		List<PolicySet> policySet = enginePolicyMapper.findPolicySet();
		List<PolicyRouter> policyRouter = enginePolicyMapper.findPolicyRouter();
		List<Policy> policys = enginePolicyMapper.findPolicy();
		List<Quota> quotas = enginePolicyMapper.findQuota();
		List<Rule> rules = enginePolicyMapper.findRule();
		List<RuleQuotaLink> rulesQuotaLinks = enginePolicyMapper.findLink();
		enginePolicies.setPolicySet(policySet);
		enginePolicies.setPolicyRouter(policyRouter);
		enginePolicies.setPolicy(policys);
		enginePolicies.setRules(rules);
		enginePolicies.setQuotas(quotas);
		enginePolicies.setRulesQuotaLinks(rulesQuotaLinks);
		return enginePolicies;
	}
	
	/**
	 * @param engineDatabase
	 * @return
	 */
	public Map<String,EnginePolicySet> buildEngineDatabase(EngineDatabase engineDatabase){
		Map<String, EnginePolicySet> finalDatabase = new ConcurrentHashMap<String,EnginePolicySet>();
		List<PolicySet> policySets = engineDatabase.getPolicySet();
		List<PolicyRouter> policyRouters = engineDatabase.getPolicyRouter();
		List<Policy> policys = engineDatabase.getPolicy();
		List<Rule> rules = engineDatabase.getRules();
		List<Quota> quotas = engineDatabase.getQuotas();
		FreeMarkerConfigBuilder builder = new FreeMarkerConfigBuilder();
		List<RuleQuotaLink> rulesQuotaLinks = engineDatabase.getRulesQuotaLinks();
		Map<String,Quota> quotaMap = new HashMap<String,Quota>();
		quotas.forEach( 
			(quota) -> {
				quotaMap.put(quota.getId()+"", quota);
				//TODO only for db ?
				if ( Quota.SOURCE_TYPE_DB_1 == quota.getSourceType()  ){
					builder.addTemplate(quota.getId()+"", quota.getQuotaContent());
				}
			}
		);
		//help to create a configuration
		freeMarkerService.setConfig(builder.create());
		policyRouters.forEach( 
			(router)-> {
				try{
			        ExpressionCompiler compiler = new ExpressionCompiler(router.getRouterExpression() );
			        CompiledExpression exp = compiler.compile();
			        router.setCompiledExpression(exp);
				}catch(Exception e){
					logger.error("Compiling router happens error, ruleId: " + router.getId() +" , ruleContent: " + router.getRouterExpression() , e);
				}
			}
		);
		for( PolicySet policySet : policySets ){
			//1. policies
			EnginePolicySet enginePolicySet = new EnginePolicySet();
			finalDatabase.put(policySet.getSceneNo(), enginePolicySet);
			//2. add all related routers to a policies
			policyRouters.forEach(
				(router)->{
					if ( policySet.getSceneNo().equals(router.getSceneNo())){
						enginePolicySet.addPolicyRouter(router);
					}
				}
			);
			//3.put policy in Map,  key-value (policyId, EnginePolicy) 
			for( Policy policy :  policys ){
				if ( policySet.getSceneNo().equals(policy.getSceneNo())){
					EnginePolicy enginePolicy = new EnginePolicy();
					//set min and max
					this.parseMinAndMax(policy);
					enginePolicy.setPolicy(policy);
					String policyId = policy.getId()+"";
					enginePolicySet.putPolicy(policyId, enginePolicy);
					for( Rule rule :  rules){
						//4. put all related rule into policy
						if ( policyId.equals(rule.getPolicyId()+"") ){
							String ruleId = rule.getId()+"";
							try{
						        ExpressionCompiler compiler = new ExpressionCompiler(rule.getRuleContent());
						        CompiledExpression exp = compiler.compile();
						        rule.setCompiledExpression(exp);
							}catch(Exception e){
								logger.error("Compiling rule happens error,ruleId: " + rule.getId() +" , ruleContent: " + rule.getRuleContent() , e);
							}
							enginePolicy.addRule(rule);
							//5. put all related quota into policy
							rulesQuotaLinks.forEach(
								(link)->{
									if ( ruleId.equals(link.getRuleId()+"") ){
										enginePolicy.addQuota(quotaMap.get(link.getQuotaId()+""));
									}
								}
							);
						}
					}
					
				}
			}
		}
		return finalDatabase;
	}

	public Map<String,Quota> getQuotaMap(){
		Map<String, Quota> quotaMap = new HashMap<String,Quota>();
		List<Quota> quotas = enginePolicyMapper.findQuota();
		quotas.forEach((quota)->{
			quotaMap.put(quota.getQuotaNo(),quota);
		});
		return quotaMap;
	}

	public EnginePolicy getMaxWeightValuePolicy(Map<String,EnginePolicy> policies){
		EnginePolicy finalPolicy = null;
		for( EnginePolicy policy :  policies.values() ){
			if ( finalPolicy == null ){
				finalPolicy = policy;
				continue;
			}
			if ( policy.getPolicy().getWeightValue().compareTo(finalPolicy.getPolicy().getWeightValue()) > 0  ){
				finalPolicy = policy;
			}
		}
		return finalPolicy;
	}
	
	
	protected void parseMinAndMax(Policy  policy){
		if ( Policy.POLICY_PATTERN_SCORE_2 == policy.getPolicyPattern() ){
			try{
				String[] value = policy.getWeightGrade().replace("[", "").replace("]", "").split(",");
				policy.setMin(new Integer(value[0].trim()));
				policy.setMax(new Integer(value[1].trim()));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void setEnginePolicyMapper(EnginePolicyMapper enginePolicyMapper) {
		this.enginePolicyMapper = enginePolicyMapper;
	}

	public void setFreeMarkerService(FreeMarkerService freeMarkerService) {
		this.freeMarkerService = freeMarkerService;
	}
	
}
