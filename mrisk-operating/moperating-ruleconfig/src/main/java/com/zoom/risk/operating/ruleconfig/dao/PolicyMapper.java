package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.Policy;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="policyMapper")
public interface PolicyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Policy record);

    int insertSelective(Policy record);

    Policy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Policy record);

    int updateByPrimaryKey(Policy record);
    
    List<Policy> selectBySceneNo(@Param("sceneNo") String sceneNo);
    
    int selectCountBySceneNo(@Param("sceneNo") String sceneNo);
    
    int updateStatus(Map<String, Object> map);
    
    List<Policy> selectBySceneNoList(List<String> sceneNoList);
    /**
     * 检查同一场景同一执行优先级的策略的数量
     * @param map
     * @return
     */
    int exists(Map<String, Object> map);
}