package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.QuotaType;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

@ZoomiBatisRepository(value="quotaTypeMapper")
public interface QuotaTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuotaType record);

    int insertSelective(QuotaType record);

    QuotaType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuotaType record);

    int updateByPrimaryKey(QuotaType record);
}