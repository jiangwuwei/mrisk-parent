/**
 * 
 */
package com.zoom.risk.platform.engine.service.impl;

import com.zoom.risk.platform.engine.mode.EngineDatabase;
import com.zoom.risk.platform.engine.mode.EnginePolicy;
import com.zoom.risk.platform.engine.mode.EnginePolicySet;
import com.zoom.risk.platform.engine.mode.EnginePolicyWrapper;
import com.zoom.risk.platform.engine.service.PolicyCacheService;
import com.zoom.risk.platform.engine.service.PolicyService;
import com.zoom.risk.platform.engine.vo.PolicyRouter;
import com.zoom.risk.platform.engine.vo.Quota;
import com.zoom.risk.platform.sharding.dbsharding.ServiceExecutorWithoutResult;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zoom.risk.platform.engine.utils.DBSelector.DS_KEY_HOLDER_OPERATING;

/**
 * @author jiangyulin
 *Nov 17, 2015
 */
@Service("policyCacheService")
public class PolicyCacheServiceImpl implements PolicyCacheService {
	private static final Logger logger = LogManager.getLogger(PolicyCacheServiceImpl.class);
	
	private int counter = 0;
	
	@Resource(name="policyService")
	private PolicyService policyService;
		
	@Resource(name="sessionManager")
	private SessionManager sessionManager;
	
	private Map<String,EnginePolicySet> engineDatabaseMap;

	private Map<String,Quota> quotaMap;

	public EnginePolicyWrapper getPolicy(String sceneNo, Map<String, Object> riskInput) {
		EnginePolicyWrapper wrapper = new EnginePolicyWrapper();
		EnginePolicySet policySet = engineDatabaseMap.get(sceneNo);
		if ( policySet == null ){
			return wrapper;
		}
		EnginePolicy finalPolicy = null;
		List<PolicyRouter> list = policySet.getPolicyRouter();
		//1. find policy from router first
		for ( PolicyRouter router : list  ){
			try{
				Boolean result = (Boolean) MVEL.executeExpression(router.getCompiledExpression(), riskInput);
				if ( result ){
					finalPolicy = policySet.getPolicy( router.getPolicyId() +"" ) ;
					wrapper.setPolicyRouter(router);
					break;
				}
			}catch(Exception e){
				logger.error("MVEL execute expression happens error, expression:" + router.getRouterExpression() , e);
			}
		}
		//2. if no one found then use max weightValue
		if ( finalPolicy == null ){
			finalPolicy = policyService.getMaxWeightValuePolicy(policySet.getPolicys());
		}
		wrapper.setEnginePolicy(finalPolicy);
        return wrapper;
	}

	@PostConstruct
	protected void init(){
		refresh();
	}
	
	public void refresh() {
		long timing = System.currentTimeMillis();
		try{
			sessionManager.runWithSession(new ServiceExecutorWithoutResult(){
				public void execute() {
                    EngineDatabase  engineDatabase = policyService.findEnginePolicies();
                    engineDatabaseMap = policyService.buildEngineDatabase(engineDatabase);
                    quotaMap = convertQuotaList(engineDatabase.getQuotas());
                    counter++;
                    logger.info("Policies have been loaded successfully, the value of counter is {}", counter );
				}
				
			}, DS_KEY_HOLDER_OPERATING);
		}catch(Exception e){
			logger.error("PolicyCacheService refresh happens error," +  e);
		}
		timing = System.currentTimeMillis() - timing;
		logger.info("EngineDatabase refresh takes {} ms ", timing);
	}

	public Map<String,Quota> getQuotaMap(){
		return quotaMap;
	}

    private Map<String,Quota> convertQuotaList(List<Quota> quotas){
        Map<String, Quota> temQuotaMap = new HashMap<String,Quota>();
        quotas.forEach((quota)->{
            temQuotaMap.put(quota.getQuotaNo(),quota);
        });
        return temQuotaMap;
    }
}
