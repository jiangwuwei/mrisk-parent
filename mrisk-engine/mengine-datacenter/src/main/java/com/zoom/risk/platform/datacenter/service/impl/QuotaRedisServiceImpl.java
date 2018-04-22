/**
 * 
 */
package com.zoom.risk.platform.datacenter.service.impl;

import com.zoom.risk.platform.datacenter.service.QuotaRedisService;
import org.springframework.stereotype.Service;

import com.zoom.risk.platform.engine.vo.Quota;

import java.util.Map;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
@Service("quotaRedisService")
public class QuotaRedisServiceImpl implements QuotaRedisService {
	
	public Object getQuotaObject(Quota quota, Map<String,Object> riskInput){
		return null;
	}
}
