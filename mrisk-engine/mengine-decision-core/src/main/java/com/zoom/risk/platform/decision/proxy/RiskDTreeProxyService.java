package com.zoom.risk.platform.decision.proxy;


import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.TQuota;
import com.zoom.risk.platform.decision.po.TRule;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public interface RiskDTreeProxyService {

    public List<TRule> mockDecisionTree2Rule(Long policyId);

    public List<DBNode> findNodesByPolicyId(Long policyId);

    public DBNode buildDecisionTree(Long policyId);

    public List<Map<String,String>> findDTPolicies();

    public Map<String,Object> findDTPolicy(String sceneNo);

    public List<TQuota> findDTQuota();
}
