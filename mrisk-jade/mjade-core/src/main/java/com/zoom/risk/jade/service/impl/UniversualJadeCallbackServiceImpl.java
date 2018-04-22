package com.zoom.risk.jade.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zoom.risk.jade.dao.HitRulesMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zoom.risk.jade.exception.DynamicSqlException;
import com.zoom.risk.jade.service.JadeCallbackService;

@Service("universualJadeCallbackService")
public class UniversualJadeCallbackServiceImpl implements JadeCallbackService {
	private static final Logger logger = LogManager.getLogger(UniversualJadeCallbackServiceImpl.class);
	
	@Resource(name="hitRulesMapper")
	private HitRulesMapper htRulesMapper;
	
	@Override
	public void beforeInsert(String sceneNo, Map<String,Object> parameterMap)throws DynamicSqlException {
		String token = (String)parameterMap.get("token");
		if ( token != null && token.length() > 500 ){
			logger.warn("Jade token is too long, and truncate it to 500 !");
			parameterMap.put("token", token.substring(0,500));
		}
	}

	@Override
	public void afterInsert(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException {
		String riskId = String.valueOf(parameterMap.get("riskId"));
		String scene = String.valueOf(parameterMap.get("scene"));
		String uid = String.valueOf(parameterMap.get("uid"));
		List<Map<String,Object>> hitRules = (List<Map<String,Object>>) parameterMap.get("hitRules");
		if ( hitRules != null && hitRules.size() > 0 ){
			for(Map<String,Object> map : hitRules ){
				map.put("riskId", riskId);
				map.put("scene", scene);
				map.put("uid", uid);
			}
			htRulesMapper.batchInsertMap(hitRules);
		}
	}

	@Override
	public void beforeUpdate(String sceneNo, Map<String,Object> parameterMap)throws DynamicSqlException {
	}

	@Override
	public void afterUpdate(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException {
	}

}
