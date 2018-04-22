/**
 * 
 */
package com.zoom.risk.platform.decision.cache.impl;

import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.DecisionTreeWrapper;
import com.zoom.risk.platform.decision.po.TQuota;
import com.zoom.risk.platform.decision.proxy.RiskDTreeProxyService;
import com.zoom.risk.platform.decision.cache.DTreeCacheService;
import com.zoom.risk.platform.decision.utils.DecisionTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
@Service("dtreeCacheService")
public class DTreeCacheServiceImpl implements DTreeCacheService {
	private static final Logger logger = LogManager.getLogger(DTreeCacheServiceImpl.class);
	public static final String LINK = "_";

	
	private Map<String,DecisionTreeWrapper> cacheDecisionTreeMap = Collections.emptyMap();

	private Map<String,TQuota> cacheQuotaMap = Collections.emptyMap();

	@Resource(name="riskDTreeProxyService")
	private RiskDTreeProxyService riskDTreeProxyService;

	public synchronized DecisionTreeWrapper getPolicyDecisionTree(String sceneNo){
		return cacheDecisionTreeMap.get(sceneNo);
	}

	public synchronized TQuota getQuota(String sceneNo, String paramName){
		return cacheQuotaMap.get(sceneNo+LINK+ paramName);
	}


	@PostConstruct
	protected void init(){
		refresh();
	}
	
	public void refresh() {
		long timing = System.currentTimeMillis();
		try{
			Map<String,DecisionTreeWrapper> tempMap = new HashMap<>();
			List<Map<String,String>> list = riskDTreeProxyService.findDTPolicies();
			for( int i = 0; i < list.size(); i++ ){
				Map<String,String> map = list.get(i);
				String sceneNo = map.get("scene_no");
				try {
					Map<String, Object> dtPolicy = riskDTreeProxyService.findDTPolicy(sceneNo);
					if (dtPolicy != null) {
						Long policyId = (Long) dtPolicy.get("id");
						String policyNo = (String) dtPolicy.get("policy_no");
						DBNode root = riskDTreeProxyService.buildDecisionTree(policyId);
						tempMap.put(sceneNo, new DecisionTreeWrapper(policyId, policyNo, root));
					}
				}catch(Exception e){
					logger.error("Loading DBNode or Building tree happens error",e);
				}
			}
			synchronized(cacheDecisionTreeMap) {
				cacheDecisionTreeMap = tempMap;
			}
		}catch(Exception e){
			logger.error("DecisionTree node refresh happens error," +  e);
		}
		try{
			Map<String,TQuota> tempQuota = new HashMap<>();
			List<TQuota> list = riskDTreeProxyService.findDTQuota();
			for( int i = 0; i < list.size(); i++ ){
				TQuota quota = list.get(i);
				quota.setParamsList(DecisionTool.buildQuotaComponet(quota.getRequestParams()));
				tempQuota.put(quota.getSceneNo()+LINK+quota.getParamName(), quota);
			}
			synchronized(tempQuota) {
				cacheQuotaMap = tempQuota;
			}
		}catch(Exception e){
			logger.error("TQuota refresh happens error," +  e);
		}

		timing = System.currentTimeMillis() - timing;
		logger.info("DTreeCacheService refresh takes {} ms ", timing);
	}

}
