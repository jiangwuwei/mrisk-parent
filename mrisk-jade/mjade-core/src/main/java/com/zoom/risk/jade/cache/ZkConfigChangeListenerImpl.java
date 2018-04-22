package com.zoom.risk.jade.cache;

import com.zoom.risk.platform.config.service.RefreshCacheService;
import com.zoom.risk.platform.config.service.impl.RefreshCacheServiceAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 风控网关dubbo服务接口。
 * @author jiangyulin
 * @version 2.0
 * @date 2015/2/18
 */

@Service("zkConfigChangeListener")
public class ZkConfigChangeListenerImpl extends RefreshCacheServiceAdapter implements RefreshCacheService {

    @Resource
    private JadeCacheService jadeCacheService;

    @Override
    public void onJadeSceneEvent(String data) {
        jadeCacheService.refreshCache();
    }
}
