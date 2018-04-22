package com.zoom.risk.operating.decision.proxy.impl;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.decision.po.*;
import com.zoom.risk.operating.decision.proxy.RiskDecisionTreeProxyService;
import com.zoom.risk.operating.decision.service.RiskDecisionTreeService;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jiangyulin on 2015/5/10.
 */
@Service("riskDecisionTreeProxyService")
public class RiskDecisionTreeProxyServiceImpl implements RiskDecisionTreeProxyService {
    private static final Logger logger = LogManager.getLogger(RiskDecisionTreeProxyServiceImpl.class);

    @Resource(name="riskDecisionTreeService")
    private RiskDecisionTreeService riskDecisionTreeService;

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    @Override
    public List<DBNode> findNodesByPolicyId(Long policyId) {
        return sessionManager.runWithSession( ()->{
                    return riskDecisionTreeService.findNodesByPolicyId(policyId);
                }, DBSelector.OPERATING_MASTER_DB);
    }

    public List<TRule> mockDecisionTree2Rule(Long policyId){
        return sessionManager.runWithSession(
                () -> riskDecisionTreeService.mockDecisionTree2Rule(policyId),
            DBSelector.OPERATING_MASTER_DB);
    }

    public String getNextNumber(String entityClass, String sceneNo){
        return sessionManager.runWithSession( ()->{
            return riskDecisionTreeService.getNextNumber(entityClass, sceneNo);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public DBNode buildDecisionTree(Long policyId){
        return sessionManager.runWithSession( ()->{
            return riskDecisionTreeService.buildDecisionTree(policyId);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public List<TRule> submitPolicyAndBuildDecisionTree(Long policyId){
        return sessionManager.runWithSession(
                () -> riskDecisionTreeService.submitPolicyAndBuildDecisionTree(policyId),
                DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public List<Scenes> selectDtScenes() {
        return sessionManager.runWithSession(
                () -> riskDecisionTreeService.selectDtScenes(),
                DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public List<DBNode> findNodesByNodeId(Long nodeId) {
        return sessionManager.runWithSession(
                () -> riskDecisionTreeService.findNodesByNodeId(nodeId),
                DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public int delNodeByNodeId(Long nodeId) {
        return sessionManager.runWithSession(
                () -> riskDecisionTreeService.delNodeByNodeId(nodeId),
                DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public List<TQuota> findQuotaByScene(String sceneNo) {
        return sessionManager.runWithSession(
                () -> riskDecisionTreeService.findQuotaByScene(sceneNo),
                DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public boolean saveNode(DBNode dbNode, Long branchParentId) {
        return sessionManager.runWithSession(
                () -> {
                    try {
                        return riskDecisionTreeService.saveNode(dbNode, branchParentId);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                    return false;
                },
                DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public List<TDim> findDimByCode(String code) {
        return sessionManager.runWithSession(
                () -> riskDecisionTreeService.findDimByCode(code),
                DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public boolean updateNode(DBNode dbNode, Integer updateType) {
        return sessionManager.runWithSession(
                () -> {
                    try {
                        return riskDecisionTreeService.updateNode(dbNode, updateType);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                    return false;
                },
                DBSelector.OPERATING_MASTER_DB);
    }
}
