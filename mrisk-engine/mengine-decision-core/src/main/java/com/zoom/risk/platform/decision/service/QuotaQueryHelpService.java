package com.zoom.risk.platform.decision.service;

import com.zoom.risk.platform.decision.po.TQuota;

import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/24.
 */
public interface QuotaQueryHelpService {

    public Object getQuotaValue(TQuota quota, Map<String,Object> riskInput);

}
