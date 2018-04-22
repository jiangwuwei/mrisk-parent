package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

@ZoomiBatisRepository(value="riskNumberMapper")
public interface RiskNumberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RiskNumber record);

    int insertSelective(RiskNumber record);

    RiskNumber selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RiskNumber record);

    int updateByPrimaryKey(RiskNumber record);
    
    int update(RiskNumber record);
    
    Integer selectSeqNo(RiskNumber record);
}