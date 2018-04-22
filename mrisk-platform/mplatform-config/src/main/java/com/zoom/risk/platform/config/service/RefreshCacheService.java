package com.zoom.risk.platform.config.service;

/**
 * @author jiangyulin
 * @version 2.0
 * @date 2015/2/18
 */
public interface RefreshCacheService{

    void dispatchEvent(String zkPath, String data);
}
