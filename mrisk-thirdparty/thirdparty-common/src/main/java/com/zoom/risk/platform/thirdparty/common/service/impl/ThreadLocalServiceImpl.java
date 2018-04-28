package com.zoom.risk.platform.thirdparty.common.service.impl;

import com.zoom.risk.platform.thirdparty.common.service.ThreadLocalService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
@Service("threadLocalService")
public class ThreadLocalServiceImpl implements ThreadLocalService {
    private ThreadLocal<Map<String,String>> riskCache;

    public ThreadLocalServiceImpl(){
        riskCache = new ThreadLocal<>();
    }

    public void ensureMap(){
        if(riskCache.get() == null){
            riskCache.set(new HashMap<>());
        }
    }

    @Override
    public String getRiskId() {
        ensureMap();
        return riskCache.get().get("riskId");
    }

    @Override
    public void putRiskId(String riskId) {
        ensureMap();
        riskCache.get().put("riskId",riskId);
    }

    @Override
    public String getServiceName() {
        ensureMap();
        return riskCache.get().get("serviceName");
    }

    @Override
    public void putServiceName(String serviceName) {
        ensureMap();
        riskCache.get().put("serviceName",serviceName);
    }

    @Override
    public String getScene() {
        ensureMap();
        return riskCache.get().get("scene");
    }

    @Override
    public void putScene(String scene) {
        ensureMap();
        riskCache.get().put("scene",scene);
    }

    @Override
    public void remove() {
        riskCache.remove();
    }
}
