package com.zoom.risk.operating.decision.dao;

import com.zoom.risk.operating.decision.po.TPolicies;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="dtPoliciesMapper")
public interface DtPoliciesMapper {
    int deleteByPrimaryKey(@Param("sceneNo") String sceneNo);

    int insert(TPolicies record);

    TPolicies selectByPrimaryKey(@Param("sceneNo") String sceneNo);

    int updateByPrimaryKey(TPolicies record);
    
    List<TPolicies> selectAll();
    
    List<TPolicies> selectPage(Map<String, Object> pageParas);
    
    Integer selectCount(@Param("sceneNo") String sceneNo);
    
    int exists(Map<String, Object> map);
    
    List<TPolicies> selectByName(Map<String, Object> map);
}