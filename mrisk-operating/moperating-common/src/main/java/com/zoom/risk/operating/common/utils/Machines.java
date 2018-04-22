package com.zoom.risk.operating.common.utils;

import com.zoom.risk.platform.ctr.util.LsManager;

public class Machines {
    public static String getMac(){
        return LsManager.getInstance().getMac();
    }
}
