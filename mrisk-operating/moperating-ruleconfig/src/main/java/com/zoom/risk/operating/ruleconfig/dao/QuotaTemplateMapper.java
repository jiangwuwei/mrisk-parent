package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.QuotaTemplate;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;

@ZoomiBatisRepository(value="quotaTemplateMapper")
public interface QuotaTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuotaTemplate record);

    int insertSelective(QuotaTemplate record);

    QuotaTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuotaTemplate record);

    int updateByPrimaryKey(QuotaTemplate record);

    List<QuotaTemplate> selectAll();
}