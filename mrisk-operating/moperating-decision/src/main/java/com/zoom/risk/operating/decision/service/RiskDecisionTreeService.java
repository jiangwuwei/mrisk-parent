package com.zoom.risk.operating.decision.service;

import com.zoom.risk.operating.decision.po.*;
import com.zoom.risk.operating.decision.po.*;

import java.util.List;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public interface RiskDecisionTreeService {

    public List<DBNode> findNodesByPolicyId(Long policyId);

    public List<TRule> mockDecisionTree2Rule(Long policyId);

    public DBNode buildDecisionTree(Long policyId);

    public DBNode buildDecisionTree(Long policyId, boolean validation);

    public DBNode buildDecisionTree(Long policyId, List<String> hitNodeIdList);

    public String getNextNumber(String entityClass, String sceneNo);

    public List<TRule> submitPolicyAndBuildDecisionTree(Long policyId);

    public List<Scenes> selectDtScenes();

    public List<DBNode> findNodesByNodeId(Long nodeId);

    public int delNodeByNodeId(Long nodeId);

    public List<TQuota> findQuotaByScene(String sceneNo);

    public boolean saveNode(DBNode dbNode, Long branchParentId) throws Exception;

    public List<TDim> findDimByCode(String code);

    public boolean updateNode(DBNode dbNode, Integer updateType) throws Exception;
}
