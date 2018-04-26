package com.zoom.risk.gateway.common.utils;

import com.zoom.risk.platform.ctr.util.LsManager;

public class Machines {
    public static String getMac(){
        return LsManager.getInstance().getMac();
    }
}
