package com.zoom.risk.platform.scard.proxy.impl;

import com.zoom.risk.platform.engine.utils.DBSelector;
import com.zoom.risk.platform.scard.mode.SCardPolicies;
import com.zoom.risk.platform.scard.proxy.SCardServiceProxy;
import com.zoom.risk.platform.scard.service.SCardService;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jiangyulin
 *May 22, 2015
 */
@Service("scardServiceProxy")
public class SCardServiceProxyImpl implements SCardServiceProxy {
    @Resource(name="scardService")
    private SCardService scardService;

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    public Map<String, SCardPolicies> buildSCardEngine(){
        return sessionManager.runWithSession(()->scardService.buildSCardEngine(), DBSelector.DS_KEY_HOLDER_OPERATING);
    }
}
