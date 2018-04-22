/**
 * 
 */
package com.zoom.risk.platform.engine.service.impl;


import com.zoom.risk.platform.engine.service.RiskExtendService;
import com.zoom.risk.platform.roster.api.RosterApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
@Service("riskExtendService")
public class RiskExtendServiceImpl implements RiskExtendService {
	private static final Logger logger = LogManager.getLogger(RiskExtendServiceImpl.class);

	@Resource(name="rosterApi")
	private RosterApi rosterApi;

	@Override
	public void decorate(Map<String, Object> riskData) {
		Map<String,Object> resultMap = null;
		long time = System.currentTimeMillis();
		try {
			resultMap = rosterApi.invokeRosterService(riskData);
			if ( !resultMap.isEmpty() ){
				riskData.putAll(resultMap);
			}
			time = System.currentTimeMillis() - time;
		}catch(Exception e) {
			logger.error("invokeExtendService method happens error", e);
		}finally {
			logger.info("InputExtendApiService takes {} ms", time);
		}
	}
}
