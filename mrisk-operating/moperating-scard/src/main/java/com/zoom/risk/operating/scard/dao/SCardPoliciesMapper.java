package com.zoom.risk.operating.scard.dao;


import com.zoom.risk.operating.scard.model.SCardPolicies;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="scardPoliciesMapper")
public interface SCardPoliciesMapper {
    int deleteByPrimaryKey(@Param("sceneNo") String sceneNo);

    int insert(SCardPolicies record);

    int insertSelective(SCardPolicies record);

    SCardPolicies selectByPrimaryKey(@Param("sceneNo") String sceneNo);

    int updateByPrimaryKeySelective(SCardPolicies record);

    int updateByPrimaryKey(SCardPolicies record);
    
    List<SCardPolicies> selectAll();
    
    List<SCardPolicies> selectPage(Map<String, Object> pageParas);
    
    Integer selectCount(@Param("sceneNo") String sceneNo);
    
    int exists(Map<String, Object> map);
    
    List<SCardPolicies> selectByName(Map<String, Object> map);
}