package com.zoom.risk.platform.datacenter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import com.zoom.risk.platform.datacenter.service.*;
import com.zoom.risk.platform.datacenter.service.QuotaCollectorService;
import com.zoom.risk.platform.datacenter.service.QuotaCollectorWorker;
import com.zoom.risk.platform.datacenter.service.QuotaInvokerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.zoom.risk.platform.engine.vo.NameValuePair;
import com.zoom.risk.platform.engine.vo.Quota;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
@Service("quotaCollectorService")
public class QuotaCollectorServiceImpl implements QuotaCollectorService {
	private static final Logger logger = LogManager.getLogger(QuotaCollectorServiceImpl.class);

	@Value("${collector.timeout}")
	private int collectorTime ;

	@Resource(name="quotaInvokerService")
	private QuotaInvokerService quotaInvokerService;
	
	@Resource(name="quotaCollectorThreadPoolExecutor")
	private ThreadPoolTaskExecutor quotaCollectorThreadPoolExecutor;
	
	@Override
	public Map<String, Object> collectQuotas(Set<Quota> quotasSet, Map<String, Object> cloneRiskInput){
		Map<String,Object> quotasMap = new HashMap<String,Object>();
		List<Future<NameValuePair>> featureList = new ArrayList<Future<NameValuePair>>();
		quotasSet.forEach(
			( quota )->{
				QuotaCollectorWorker work = new QuotaCollectorWorker(quota, cloneRiskInput, quotaInvokerService);
				Future<NameValuePair> feature = quotaCollectorThreadPoolExecutor.submit(work);
				featureList.add(feature);
			}
		);
		//if fail to get quota then it will cause rule to fail too.
		int elapsedTime = 0;
		for( Future<NameValuePair> f : featureList ){
			long takingTime = System.currentTimeMillis();
			String quotaNo = "";
			try {
				int waitTime = collectorTime-elapsedTime;
				if ( waitTime <= 0 ){
					break;
				}
				NameValuePair  value = f.get(waitTime, TimeUnit.MILLISECONDS);
				quotaNo = value.getKey();
				quotasMap.put(value.getKey(), value.getValue());
			} catch (TimeoutException tx) {
				f.cancel(true);
				logger.error("TimeoutException happens",tx);
			} catch (Exception tx) {
				f.cancel(true);
				logger.error("",tx);
			}finally {
				takingTime = System.currentTimeMillis() - takingTime ;
				elapsedTime += takingTime;
				logger.info("QuotaNo : {} takes {} ms to collect, elapsedTime {}", quotaNo, takingTime,elapsedTime );
			}
		}
		return quotasMap;
	}
}
