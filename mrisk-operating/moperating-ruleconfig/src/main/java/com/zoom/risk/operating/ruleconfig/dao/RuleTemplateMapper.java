package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.RuleTemplate;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

@ZoomiBatisRepository(value="ruleTemplateMapper")
public interface RuleTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RuleTemplate record);

    int insertSelective(RuleTemplate record);

    RuleTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuleTemplate record);

    int updateByPrimaryKey(RuleTemplate record);
}