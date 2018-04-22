package com.zoom.risk.platform.decision.api;

import java.util.Map;

/**
 * Created by jiangyulin on 2015/5/17.
 */
public interface DTreeEngineApi {

    public DTreeResponse evaluate(Map<String,Object> riskInput);

}
