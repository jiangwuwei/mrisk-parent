package com.zoom.risk.operating.decision.dao;

import com.zoom.risk.operating.decision.po.TPolicy;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="dtPolicyMapper")
public interface DtPolicyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TPolicy record);

    TPolicy selectByPrimaryKey(Long id);

    int updateByPrimaryKey(TPolicy record);
    
    List<TPolicy> selectBySceneNo(@Param("sceneNo") String sceneNo);
    
    int selectCountBySceneNo(@Param("sceneNo") String sceneNo);
    
    int updateStatus(Map<String, Object> map);
    
    List<TPolicy> selectBySceneNoList(List<String> sceneNoList);
    /**
     * 检查同一场景同一执行优先级的策略的数量
     * @param map
     * @return
     */
    int exists(Map<String, Object> map);
}