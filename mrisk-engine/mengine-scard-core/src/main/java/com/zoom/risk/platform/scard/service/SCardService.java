package com.zoom.risk.platform.scard.service;

import com.zoom.risk.platform.scard.mode.SCardPolicies;

import java.util.Map;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public interface SCardService {

    public Map<String, SCardPolicies> buildSCardEngine() ;
}
