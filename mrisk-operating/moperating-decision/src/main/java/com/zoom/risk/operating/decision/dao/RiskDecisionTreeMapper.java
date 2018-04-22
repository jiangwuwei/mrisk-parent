package com.zoom.risk.operating.decision.dao;

import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import com.zoom.risk.operating.decision.po.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JiangMi, Mar, 17, 2015
 */

@ZoomiBatisRepository(value="riskDecisionTreeMapper")
public interface RiskDecisionTreeMapper {
	
	public List<DBNode> findNodesByPolicyId(@Param("policyId") Long policyId);

	public void saveDecisionTree(List<TRule> list);

	public Integer getNextNumber(@Param("entityClass") String entityClass, @Param("sceneNo") String sceneNo);

	public void saveNumber(@Param("entityClass") String entityClass, @Param("sceneNo") String sceneNo);

	public Integer increaseNumber(@Param("entityClass") String entityClass, @Param("sceneNo") String sceneNo);

	public List<Scenes> selectDtScenes();

	public List<DBNode> findNodesByNodeId(@Param("nodeId") Long nodeId);

	public int delNodeByNodeId(@Param("nodeId") Long nodeId);

	public List<TQuota> findQuotaByScene(@Param("sceneNo") String sceneNo);

	public int saveNode(DBNode dbNode);

	public int updateNodeParent(@Param("parentId") Long parentId, @Param("id") Long id);

	public List<TDim> findDimByCode(@Param("code") String code);

	public int updateBranchNode(DBNode dbNode);

	public int updateLeafNode(DBNode dbNode);

	public int updateParentNode(DBNode dbNode);
}
