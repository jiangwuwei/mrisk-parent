package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.Policies;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="policiesMapper")
public interface PoliciesMapper {
    int deleteByPrimaryKey(@Param("sceneNo") String sceneNo);

    int insert(Policies record);

    int insertSelective(Policies record);

    Policies selectByPrimaryKey(@Param("sceneNo") String sceneNo);

    int updateByPrimaryKeySelective(Policies record);

    int updateByPrimaryKey(Policies record);
    
    List<Policies> selectAll();
    
    List<Policies> selectPage(Map<String, Object> pageParas);
    
    Integer selectCount(@Param("sceneNo") String sceneNo);
    
    int exists(Map<String, Object> map);
    
    List<Policies> selectByName(Map<String, Object> map);
}