package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.Scenes;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="scenesMapper")
public interface ScenesMapper {
    int deleteByPrimaryKey(@Param("sceneNo") String sceneNo);

    int insert(Scenes record);

    int insertSelective(Scenes record);

    Scenes selectByPrimaryKey(@Param("sceneNo") String sceneNo);

    int updateByPrimaryKeySelective(Scenes record);

    int updateByPrimaryKey(Scenes record);
    
    List<Scenes> selectPage(Map<String, Object> pageParas);
   
    int selectCount(@Param("sceneNo") String sceneNo, @Param("sceneType") Integer sceneType);
    
    Integer existScene(Map<String, Object> map);

    List<Scenes> selectByType(@Param("sceneType") Integer sceneType);
}