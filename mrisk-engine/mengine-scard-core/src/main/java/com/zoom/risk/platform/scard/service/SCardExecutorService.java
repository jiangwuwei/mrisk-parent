package com.zoom.risk.platform.scard.service;

import com.zoom.risk.platform.scard.mode.SCardParam;
import com.zoom.risk.platform.scard.mode.SCardParamVo;
import com.zoom.risk.platform.scard.mode.SCardRule;

import java.util.Map;
import java.util.Set;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public interface SCardExecutorService {

    public SCardParamVo execute(SCardParam sCardParam, Map<String, Object> riskInput);

    public Float executeWeight(Set<SCardParamVo> sCardParamVo);

    public SCardRule executeRules(Set<SCardRule> sCardRules, Map<String, Object> riskInput);

}
