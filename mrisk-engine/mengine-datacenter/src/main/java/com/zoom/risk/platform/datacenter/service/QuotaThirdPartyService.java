package com.zoom.risk.platform.datacenter.service;

import com.zoom.risk.platform.engine.vo.Quota;

import java.util.Map;

/**
 * Created by jiangyulin on 2015/12/2.
 */
public interface QuotaThirdPartyService {

    public Map<String, Object> getQuotaObject(Quota quota, Map<String,Object> riskInput);

}
