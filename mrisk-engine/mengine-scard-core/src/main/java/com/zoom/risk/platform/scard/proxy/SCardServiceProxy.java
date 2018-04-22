package com.zoom.risk.platform.scard.proxy;

import com.zoom.risk.platform.scard.mode.SCardPolicies;

import java.util.Map;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public interface SCardServiceProxy {

    public Map<String, SCardPolicies> buildSCardEngine();
}
