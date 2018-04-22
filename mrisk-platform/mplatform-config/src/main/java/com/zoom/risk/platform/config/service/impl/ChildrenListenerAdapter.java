package com.zoom.risk.platform.config.service.impl;

import com.zoom.risk.platform.config.service.RefreshCacheService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jiangyulin
 * @version 2.0
 * @date 2015/2/18
 */
@Service("zkConfigAdapterListener")
public class ChildrenListenerAdapter implements PathChildrenCacheListener {
    private static final Logger logger = LogManager.getLogger(ChildrenListenerAdapter.class);

    @Resource(name="zkConfigChangeListener")
    private RefreshCacheService refreshCacheService;

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        logger.info("开始进行事件分析:-----");
        ChildData data = event.getData();
        switch (event.getType()) {
            case CHILD_ADDED:
                break;
            case CHILD_REMOVED:
                break;
            case CHILD_UPDATED:
                logger.info("Path:{}, data:{}", data.getPath(), new String(data.getData()));
                if ( refreshCacheService != null ){
                    try {
                        refreshCacheService.dispatchEvent(data.getPath(),new String(data.getData()));
                    }catch (Exception e){
                        logger.error("zk事件通知执行时发生错误",e);
                    }
                }
                break;
            default:
                break;
        }
    }
}
