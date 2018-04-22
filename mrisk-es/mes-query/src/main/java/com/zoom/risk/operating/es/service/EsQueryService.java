package com.zoom.risk.operating.es.service;

import java.util.Map;

import com.zoom.risk.operating.es.vo.EventInputModel;
import com.zoom.risk.operating.es.vo.EventOutputModel;
import com.zoom.risk.operating.es.vo.EventRuleInfo;

public interface EsQueryService {
	
	public EventOutputModel queryEvents(EventInputModel eventInputModel);
	
	public EventRuleInfo queryRuleDetail(String sceneNo, String riskDate);

	public Map<String,Object> queryEventDetail(String riskDate,String riskId);

	public EventRuleInfo queryRelationship(String parameterName, String parameterValue, String riskDate);
	
}


