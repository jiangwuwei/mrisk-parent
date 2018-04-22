package com.zoom.risk.platform.config.service.impl;

import com.zoom.risk.platform.config.service.CuratorService;
import com.zoom.risk.platform.config.utils.EventConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author jiangyulin
 * @version 2.0
 * @date 2015/2/18
 */
@Service("curatorService")
public class CuratorServiceImpl implements CuratorService {
    private static final Logger logger = LogManager.getLogger(CuratorServiceImpl.class);
    private static final String CONFIG_PATH = "/zoom/risk/config/cache";
    private static final String SEPARATOR ="/";

    private CuratorFramework client;
    private PathChildrenCache cache;

    @Value("${dubbo.registry.address}")
    private String dubboZkStr;

    @Resource(name="zkConfigAdapterListener")
    private PathChildrenCacheListener zkConfigAdapterListener;

    @PostConstruct
    public void startup() {
        try {
            client =  CuratorFrameworkFactory.builder()
                    .connectString( adaptStr(dubboZkStr) )
                    .connectionTimeoutMs(15000)
                    .sessionTimeoutMs(20000)
                    .canBeReadOnly( false )
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .defaultData( null )
                    .build();
            client.start();
            init();
        } catch (Exception e) {
            logger.error("ZK的配置服务错误",e);
        }
    }

    private void init() throws Exception {
        for(String config : EventConstant.configs ){
            String path = CONFIG_PATH + SEPARATOR + config;
            Stat stat = client.checkExists().forPath( path );
            if ( stat == null ) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "1".getBytes());
                Thread.sleep(200);
            }
        }
        cache = new PathChildrenCache(client, CONFIG_PATH, true);
        cache.getListenable().addListener(zkConfigAdapterListener);
        cache.start();
    }

    @PreDestroy
    public void destroy() {
        if ( client != null ){
            client.close();
        }
        if ( cache != null ){
            try {
                cache.close();
            } catch (IOException e) {
                logger.error("",e);
            }
        }
    }

    private String adaptStr(String strConn){
        return  strConn.replaceAll("zookeeper://","").replaceAll("\\?backup=",",");
    }

    public void refresh(String config){
        try {
            client.setData().forPath(CONFIG_PATH + SEPARATOR +  config , "1".getBytes());
        } catch (Exception e) {
            logger.error("更新配置错误",e);
        }
    }
}
