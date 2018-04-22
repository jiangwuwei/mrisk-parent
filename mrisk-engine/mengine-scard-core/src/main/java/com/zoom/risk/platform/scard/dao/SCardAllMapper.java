package com.zoom.risk.platform.scard.dao;

import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import com.zoom.risk.platform.scard.mode.*;

import java.util.List;

/**
 * @author jiangyulin
 *May 22, 2015
 */
@ZoomiBatisRepository(value="scardAllMapper")
public interface SCardAllMapper {

    List<SCardPolicies> selectSCardPolicies();

    List<SCard> selectSCard();

    List<SCardParam> selectSCardParam();

    List<SCardParamRoute> selectSCardParamRoute();

    List<SCardRule> selectSCardRule();

    List<SCardRouter> selectSCardRouter();
}
