package com.zoom.risk.platform.config.service.impl;

import com.zoom.risk.platform.config.service.RefreshCacheService;
import com.zoom.risk.platform.config.utils.EventConstant;

/**
 * @author jiangyulin
 * @version 2.0
 * @date 2015/2/18
 */
public abstract class RefreshCacheServiceAdapter implements RefreshCacheService {
    @Override
    public void dispatchEvent(String zkPath, String data) {
        String path = zkPath.substring(zkPath.lastIndexOf("/")+1);
        if (EventConstant.EVENT_ANTIFRAUND.equals(path)){
            this.onAntifraudEvent(data);
        }else if (EventConstant.EVENT_DTREE.equals(path)){
            this.onDtreeEvent(data);
        }else if (EventConstant.EVENT_SCARD.equals(path)){
            this.onScardEvent(data);
        }else if (EventConstant.EVENT_ROSTERCONFIG.equals(path)){
            this.onRosterConfigEvent(data);
        }else if (EventConstant.EVENT_JADE_SCENES.equals(path)){
            this.onJadeSceneEvent(data);
        }
    }

    public void onAntifraudEvent(String data){

    }

    public void onScardEvent(String data){

    }

    public void onDtreeEvent(String data){

    }

    public void onRosterConfigEvent(String data){

    }

    public void onJadeSceneEvent(String data){

    }
}
