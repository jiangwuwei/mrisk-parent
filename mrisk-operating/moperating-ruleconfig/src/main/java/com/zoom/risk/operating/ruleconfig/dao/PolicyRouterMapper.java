package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.PolicyRouter;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@ZoomiBatisRepository(value="policyRouterMapper")
public interface PolicyRouterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PolicyRouter record);

    PolicyRouter selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PolicyRouter record);

    int deleteByPolicyId(Long policyId);

    List<PolicyRouter> selectBySceneNo(@Param("sceneNo") String sceneNo);

    List<String> selectDistinctSceneNo();

    Integer existPolicy(@Param("policyId") Long policyId);
}