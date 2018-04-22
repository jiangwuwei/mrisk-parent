package com.zoom.risk.operating.scard.dao;

import com.zoom.risk.operating.scard.model.SCardRule;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="scardRuleMapper")
public interface SCardRuleMapper {

    List<SCardRule> getRulesBySCard(Long scardId);

    void deleteByPrimaryKey(Long id);

    Long saveScardRule(SCardRule scardRule);

    void updateByPrimaryKey(SCardRule scardRule);

}