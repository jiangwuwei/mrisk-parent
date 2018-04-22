package com.zoom.risk.platform.scard.cache;

import com.zoom.risk.platform.scard.mode.EnginePolices;

import java.util.Map;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public interface SCardCacheService {

    public EnginePolices getSCardPolicies(String sceneNo, Map<String,Object> cloneRiskInput);

    public void refresh();

}
