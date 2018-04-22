package com.zoom.risk.platform.decision.proxy.impl;


import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.TQuota;
import com.zoom.risk.platform.decision.po.TRule;
import com.zoom.risk.platform.decision.proxy.RiskDTreeProxyService;
import com.zoom.risk.platform.decision.service.RiskDTreeService;
import com.zoom.risk.platform.engine.utils.DBSelector;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/10.
 */
@Service("riskDTreeProxyService")
public class RiskDTreeProxyServiceImpl implements RiskDTreeProxyService {
    private static final Logger logger = LogManager.getLogger(RiskDTreeProxyServiceImpl.class);

    @Resource(name="riskDTreeService")
    private RiskDTreeService riskDTreeService;

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    @Override
    public List<DBNode> findNodesByPolicyId(Long policyId) {
        return sessionManager.runWithSession( ()->{
                    return riskDTreeService.findNodesByPolicyId(policyId);
                }, DBSelector.DS_KEY_HOLDER_OPERATING);
    }


    public DBNode buildDecisionTree(Long policyId){
        return sessionManager.runWithSession( ()->{
            return riskDTreeService.buildDecisionTree(policyId);
        }, DBSelector.DS_KEY_HOLDER_OPERATING);
    }

    public List<Map<String,String>> findDTPolicies(){
        return sessionManager.runWithSession( ()->{
            return riskDTreeService.findDTPolicies();
        }, DBSelector.DS_KEY_HOLDER_OPERATING);
    }

    public Map<String,Object> findDTPolicy(String sceneNo){
        return sessionManager.runWithSession( ()->{
            return riskDTreeService.findDTPolicy(sceneNo);
        }, DBSelector.DS_KEY_HOLDER_OPERATING);
    }

    public List<TQuota> findDTQuota(){
        return sessionManager.runWithSession( ()->{
            return riskDTreeService.findDTQuota();
        }, DBSelector.DS_KEY_HOLDER_OPERATING);
    }

    public List<TRule> mockDecisionTree2Rule(Long policyId){
        return sessionManager.runWithSession(
                () -> riskDTreeService.mockDecisionTree2Rule(policyId),
                DBSelector.DS_KEY_HOLDER_OPERATING);
    }
}
