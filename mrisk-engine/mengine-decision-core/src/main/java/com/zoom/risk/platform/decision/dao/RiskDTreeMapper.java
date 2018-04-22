package com.zoom.risk.platform.decision.dao;

import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.TQuota;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JiangMi, Mar, 17, 2017
 */

@ZoomiBatisRepository(value="riskDTreeMapper")
public interface RiskDTreeMapper {

	public List<DBNode> findNodesByPolicyId(@Param("policyId") Long policyId);

	public List<Map<String, String>> findDTPolicies();

	public Map<String, Object> findDTPolicy(@Param("sceneNo") String sceneNo);

	/**
	 * find all quota and cache them
	 * @return
	 */
	public List<TQuota> findDTQuota();
}
