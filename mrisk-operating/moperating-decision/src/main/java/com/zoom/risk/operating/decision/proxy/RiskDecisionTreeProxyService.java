package com.zoom.risk.operating.decision.proxy;

import com.zoom.risk.operating.decision.po.*;
import com.zoom.risk.operating.decision.po.*;

import java.util.List;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public interface RiskDecisionTreeProxyService {

    public List<DBNode> findNodesByPolicyId(Long policyId);
    public List<TRule> mockDecisionTree2Rule(Long policyId);

    public List<TRule> submitPolicyAndBuildDecisionTree(Long policyId);

    public String getNextNumber(String entityClass, String sceneNo);

    public DBNode buildDecisionTree(Long policyId);

    public List<Scenes> selectDtScenes();

    public List<DBNode> findNodesByNodeId(Long nodeId);

    public int delNodeByNodeId(Long nodeId);

    public List<TQuota> findQuotaByScene(String sceneNo);

    public boolean saveNode(DBNode dbNode, Long branchParentId);

    public List<TDim> findDimByCode(String code);

    public boolean updateNode(DBNode dbNode, Integer updateType);
}

