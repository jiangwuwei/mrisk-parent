package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.QuotaDim;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;

@ZoomiBatisRepository(value="quotaDimMapper")
public interface QuotaDimMapper {

    int insert(QuotaDim record);

    int insertSelective(QuotaDim record);


    int updateByPrimaryKeySelective(QuotaDim record);

    int updateByPrimaryKey(QuotaDim record);
    
    List<QuotaDim> selectAll();
}