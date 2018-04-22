package com.zoom.risk.platform.scard.api;

import java.util.Map;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public interface SCardEngineApi {

    public ScoreCardResponse evaluate(Map<String,Object> riskInput);

}
