package com.zoom.risk.operating.decision.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.operating.decision.common.Constants;
import com.zoom.risk.operating.decision.dao.RiskDecisionTreeMapper;
import com.zoom.risk.operating.decision.po.*;
import com.zoom.risk.operating.decision.po.*;
import com.zoom.risk.operating.decision.service.DecisionNodeService;
import com.zoom.risk.operating.decision.service.RiskDecisionTreeService;
import com.zoom.risk.operating.decision.utils.RouteTools;
import com.zoom.risk.operating.decision.vo.RouteMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/10.
 */
@Service("riskDecisionTreeService")
public class RiskDecisionTreeServiceImpl implements RiskDecisionTreeService {
    private static final Logger logger = LogManager.getLogger(RiskDecisionTreeServiceImpl.class);

    @Resource(name = "riskDecisionTreeMapper")
    private RiskDecisionTreeMapper riskDecisionTreeMapper;

    @Resource(name = "decisionNodeService")
    private DecisionNodeService decisionNodeService;

    @Override
    @Transactional(readOnly = true)
    public List<DBNode> findNodesByPolicyId(Long policyId) {
        return riskDecisionTreeMapper.findNodesByPolicyId(policyId);
    }

    protected List<TRule> buildDecisionTreeRules(Long policyId, String sceneNo, List<DBNode> list) {
        logger.info("List<DBNode> allNodes : {}", JSON.toJSONString(list));
        Map<Long, DBNode> temMap = new HashMap<>();
        list.forEach((node) -> {
            temMap.put(node.getId(), node);
        });
        List<TRule> resultRules = new ArrayList<>();
        List<List<RouteMode>> allRoutes = decisionNodeService.generateRoutes(list);
        logger.info("List<List<RouteMode>> allRoutes : {}", JSON.toJSONString(allRoutes));
        for (int i = 0; i < allRoutes.size(); i++) {
            List<RouteMode> singleRoute = allRoutes.get(i);
            logger.info("List<RouteMode>: {}", JSON.toJSONString(singleRoute));
            RouteMode routeLast = singleRoute.get(0);
            DBNode routeLeafNode = temMap.get(routeLast.getNodeId());
            TRule rule = new TRule(policyId, sceneNo, singleRoute.size(), RouteTools.getParamStr(singleRoute), routeLeafNode.getDecisionCode(), routeLeafNode.getActionCode(), routeLeafNode.getReason());
            String[] values = RouteTools.getRouteExpression(singleRoute);
            rule.setRuleContent(values[0]);
            rule.setName(values[1]);
            rule.setDescription(values[2]);
            rule.setScore(RouteTools.getRouteScore(singleRoute, temMap));
            resultRules.add(rule);
        }

        return resultRules;
    }


    protected void saveDecisionTree(List<TRule> list) {
        list.forEach((rule) -> {
            String ruleNo = getNextNumber("TR", rule.getSceneNo());
            rule.setRuleNo(ruleNo);
            rule.setStatus(2);
        });
        logger.info("List<Rule> allRules : " + JSON.toJSONString(list));
        riskDecisionTreeMapper.saveDecisionTree(list);
    }

    @Transactional(readOnly = false)
    public List<TRule> submitPolicyAndBuildDecisionTree(Long policyId) {
        List<DBNode> allNodes = this.findNodesByPolicyId(policyId);
        List<TRule> allRules = buildDecisionTreeRules(policyId, allNodes.get(0).getSceneNo(), allNodes);
        this.saveDecisionTree(allRules);
        return allRules;
    }

    protected void mockDecisionTree(List<TRule> list) {
        list.forEach((rule) -> {
            String ruleNo = "TR" + rule.getSceneNo() + "_0000000";
            rule.setRuleNo(ruleNo);
            rule.setStatus(2);
        });
        logger.info("List<Rule> allRules : " + JSON.toJSONString(list));
    }

    @Transactional(readOnly = true)
    public List<TRule> mockDecisionTree2Rule(Long policyId) {
        List<DBNode> allNodes = this.findNodesByPolicyId(policyId);
        List<TRule> allRules = buildDecisionTreeRules(policyId, allNodes.get(0).getSceneNo(), allNodes);
        this.mockDecisionTree(allRules);
        return allRules;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String getNextNumber(String entityClass, String sceneNo) {
        Integer value = riskDecisionTreeMapper.increaseNumber(entityClass, sceneNo);
        if (value > 0) {
            value = riskDecisionTreeMapper.getNextNumber(entityClass, sceneNo);
        } else {
            riskDecisionTreeMapper.saveNumber(entityClass, sceneNo);
            value = 1;
        }
        return entityClass + sceneNo + "_" + String.format("%07d", value);
    }

    @Transactional(readOnly = true)
    public DBNode buildDecisionTree(Long policyId) {
        List<DBNode> list = riskDecisionTreeMapper.findNodesByPolicyId(policyId);
        return decisionNodeService.buildDecisionTree(list);
    }

    @Transactional(readOnly = true)
    public DBNode buildDecisionTree(Long policyId, boolean validation) {
        List<DBNode> list = riskDecisionTreeMapper.findNodesByPolicyId(policyId);
        return decisionNodeService.buildDecisionTree(list, validation);
    }

    @Transactional(readOnly = true)
    public DBNode buildDecisionTree(Long policyId, List<String> hitNodeIdList) {
        List<DBNode> list = riskDecisionTreeMapper.findNodesByPolicyId(policyId);
        for (DBNode dbNode: list) {
            if(hitNodeIdList.indexOf(dbNode.getId()+"") >= 0){
                dbNode.setIsHitNode(1);
            }
        }
        return decisionNodeService.buildDecisionTree(list, false);
    }


    @Override
    public List<Scenes> selectDtScenes() {
        return riskDecisionTreeMapper.selectDtScenes();
    }

    @Override
    public List<DBNode> findNodesByNodeId(Long nodeId) {
        return riskDecisionTreeMapper.findNodesByNodeId(nodeId);
    }

    @Override
    public int delNodeByNodeId(Long nodeId) {
        return riskDecisionTreeMapper.delNodeByNodeId(nodeId);
    }

    @Override
    public List<TQuota> findQuotaByScene(String sceneNo) {
        return riskDecisionTreeMapper.findQuotaByScene(sceneNo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveNode(DBNode dbNode, Long branchParentId) throws Exception{
        if (dbNode.getParentId() == null) {//parent node hasn't saved
            this.saveParentNode(dbNode, branchParentId);
        }else{
            DBNode parentNode = new DBNode();
            parentNode.setQuotaId(dbNode.getQuotaId());
            parentNode.setChineseName(dbNode.getChineseName());
            parentNode.setParamName(dbNode.getParamName());
            parentNode.setId(dbNode.getParentId());
            riskDecisionTreeMapper.updateParentNode(parentNode);
        }
        dbNode.setNodeNo(this.getNextNumber(Constants.ENTITY_CLASS_NODE,dbNode.getSceneNo()));
        //only save path
        dbNode.setChineseName(null);
        dbNode.setParamName(null);
        dbNode.setQuotaId(null);
        int ret = riskDecisionTreeMapper.saveNode(dbNode);
        return ret==1?true:false;
    }

    @Override
    public boolean updateNode(DBNode dbNode, Integer updateType) throws Exception{
        int ret;
        if(updateType.intValue() == 2){
            ret = riskDecisionTreeMapper.updateBranchNode(dbNode);
        }else{
            ret =riskDecisionTreeMapper.updateLeafNode(dbNode);
        }
        return ret == 1? true : false;
    }

    @Override
    public List<TDim> findDimByCode(String code) {
        return riskDecisionTreeMapper.findDimByCode(code);
    }

    /**
     * if branchParentId is null, insert root node and update root parent id as it's own id
     * if branchParentId is not null, the
     * set branch node parent id as root node's id
     * @param branchNode
     * @throws Exception
     */
    private void saveParentNode(DBNode branchNode, Long branchParentId) throws Exception {
        DBNode parentNode = new DBNode();
        parentNode.setChineseName(branchNode.getChineseName());
        parentNode.setParamName(branchNode.getParamName());
        parentNode.setQuotaId(branchNode.getQuotaId());
        parentNode.setPolicyId(branchNode.getPolicyId());
        parentNode.setSceneNo(branchNode.getSceneNo());
        parentNode.setNodeNo(this.getNextNumber(Constants.ENTITY_CLASS_NODE, branchNode.getSceneNo()));
        //it's branch node
        if(branchParentId != null){
            parentNode.setParentId(branchParentId);
            parentNode.setNodeType(Node.NODE_TYPE_BRANCH);
        }else{
            //it's root node, the score for root node should be 0
            parentNode.setScore(0);
            parentNode.setNodeType(Node.NODE_TYPE_ROOT);
        }
        riskDecisionTreeMapper.saveNode(parentNode);
        if (parentNode.getId() == null) {
            logger.error("failed to save root node, chinese name:" + parentNode.getChineseName() + ", " +
                    "quota id:" + parentNode.getQuotaId() + ", policy id:" + parentNode.getPolicyId());
            throw new Exception();
        }
        branchNode.setParentId(parentNode.getId());
        //it's root node, the parent id should be it's own id
        if(branchParentId == null){
            int ret = riskDecisionTreeMapper.updateNodeParent(parentNode.getId(), parentNode.getId());
            if (ret != 1) {
                throw new Exception("can't update root node parent id, root id:" + parentNode.getId());
            }
        }

    }

    /**
     * update node parent id
     * @param parentId
     * @param id
     * @return
     */
    private int updateNodeParentId(Long parentId, Long id) {
        return riskDecisionTreeMapper.updateNodeParent(parentId, id);
    }

}
