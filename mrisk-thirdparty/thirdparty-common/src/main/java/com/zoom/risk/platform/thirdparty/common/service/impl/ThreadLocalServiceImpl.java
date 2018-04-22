package com.zoom.risk.platform.thirdparty.common.service.impl;

import com.zoom.risk.platform.thirdparty.common.service.ThreadLocalService;
import org.springframework.stereotype.Service;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
@Service("threadLocalService")
public class ThreadLocalServiceImpl implements ThreadLocalService {
    private ThreadLocal<String> riskCache;

    public ThreadLocalServiceImpl(){
        riskCache = new ThreadLocal<>();
    }

    @Override
    public String getRiskId() {
        return riskCache.get();
    }

    @Override
    public void putRiskId(String riskId) {
        riskCache.set(riskId);
    }

    @Override
    public void remove() {
        riskCache.remove();
    }
}
