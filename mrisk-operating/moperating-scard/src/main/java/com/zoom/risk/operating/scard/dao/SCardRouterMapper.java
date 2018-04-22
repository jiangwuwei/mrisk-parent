package com.zoom.risk.operating.scard.dao;

import com.zoom.risk.operating.scard.model.SCardRouter;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="scardRouterMapper")
public interface SCardRouterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SCardRouter record);

    SCardRouter selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SCardRouter record);

    int deleteByPolicyId(Long policyId);

    List<SCardRouter> selectBySceneNo(@Param("sceneNo") String sceneNo);

    List<String> selectDistinctSceneNo();

    Integer existPolicy(@Param("scardId") Long scardId);
}