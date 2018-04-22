package com.zoom.risk.platform.refresh;

import com.zoom.risk.platform.config.service.RefreshCacheService;
import com.zoom.risk.platform.config.service.impl.RefreshCacheServiceAdapter;
import com.zoom.risk.platform.decision.cache.DTreeCacheService;
import com.zoom.risk.platform.engine.service.PolicyCacheService;
import com.zoom.risk.platform.roster.api.RosterInnerApiService;
import com.zoom.risk.platform.scard.cache.SCardCacheService;
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
    private PolicyCacheService policyCacheService;
    @Resource
    private DTreeCacheService  dtreeCacheService;
    @Resource
    private SCardCacheService scardCacheService;
    @Resource
    private RosterInnerApiService rosterInnerApiService;

    @Override
    public void onAntifraudEvent(String data) {
        policyCacheService.refresh();
    }

    @Override
    public void onScardEvent(String data) {
        scardCacheService.refresh();
    }

    @Override
    public void onDtreeEvent(String data) {
        dtreeCacheService.refresh();
    }

    @Override
    public void onRosterConfigEvent(String data) {
        rosterInnerApiService.refresh();
    }
}
