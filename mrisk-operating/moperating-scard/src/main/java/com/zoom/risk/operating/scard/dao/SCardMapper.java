package com.zoom.risk.operating.scard.dao;

import com.zoom.risk.operating.scard.model.SCard;
import com.zoom.risk.operating.scard.model.SCardRule;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="scardDefinitionMapper")
public interface SCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SCard record);

    int insertSelective(SCard record);

    SCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SCard record);

    int updateByPrimaryKey(SCard record);
    
    List<SCard> selectBySceneNo(@Param("sceneNo") String sceneNo);
    
    int selectCountBySceneNo(@Param("sceneNo") String sceneNo);
    
    int updateStatus(Map<String, Object> map);
    
    List<SCard> selectBySceneNoList(List<String> sceneNoList);

    List<SCardRule> findAllRules(Long scardId);
    /**
     * 检查同一场景同一执行优先级的策略的数量
     * @param map
     * @return
     */
    int exists(Map<String, Object> map);
}