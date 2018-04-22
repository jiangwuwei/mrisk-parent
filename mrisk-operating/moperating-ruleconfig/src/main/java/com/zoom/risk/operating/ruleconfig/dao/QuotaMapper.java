package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.Quota;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="quotaMapper")
public interface QuotaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Quota record);

    int insertSelective(Quota record);

    Quota selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Quota record);

    int updateByPrimaryKey(Quota record);
    
    List<Quota> selectBySceneNo(Map<String, Object> map);
    /**
     * 查询分页指标
     * @param pageParas sceneNo, limit, offset
     * @return
     */
    List<Quota> selectPage(Map<String, Object> pageParas);
    
    Integer selectCount(String sceneNo);
    
    List<Long> selectIdsByQuotaNos(List<String> quotaNoList);
    
    int updateStatus(Map<String, Object> map);
    
    List<Quota> selectByName(Map<String, Object> map);
}