package com.zoom.risk.platform.roster.api;

import java.util.Map;

/**
 * Created by jiangyulin on 2017/3/13.
 */
public interface RosterApi {

    public Map<String,Object> invokeRosterService(Map<String,Object> riskInput);

}

