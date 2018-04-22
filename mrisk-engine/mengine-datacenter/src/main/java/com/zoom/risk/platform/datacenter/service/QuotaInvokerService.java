package com.zoom.risk.platform.datacenter.service;

import com.zoom.risk.platform.engine.vo.Quota;

import java.util.Map;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
public interface QuotaInvokerService {

    public Object collectQuota(Quota quota, Map<String,Object> riskInput);

}
