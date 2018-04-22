/**
 * 
 */
package com.zoom.risk.platform.datacenter.service;

import com.zoom.risk.platform.engine.vo.NameValuePair;
import com.zoom.risk.platform.engine.vo.Quota;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
public class QuotaCollectorWorker implements Callable<NameValuePair>{
	private static final Logger logger = LogManager.getLogger(QuotaCollectorWorker.class);

	private QuotaInvokerService quotaInvokerService;
	private Map<String,Object> riskInput;
	private Quota quota;
	
	public QuotaCollectorWorker(Quota quota, Map<String,Object> riskInput, QuotaInvokerService quotaInvokerService){
		this.quotaInvokerService = quotaInvokerService;
		this.riskInput = riskInput;
		this.quota = quota;
	}

	protected NameValuePair collect(Quota quota){
		String key = quota.getQuotaNo();
		Object value = quotaInvokerService.collectQuota(quota,riskInput);
		return new NameValuePair(key,value);
	}


	@Override
	public NameValuePair call() throws Exception {
		return collect(quota);
	}
}
