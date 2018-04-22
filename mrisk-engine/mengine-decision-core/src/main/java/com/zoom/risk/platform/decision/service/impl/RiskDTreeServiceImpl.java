package com.zoom.risk.platform.decision.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.decision.dao.RiskDTreeMapper;
import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.TQuota;
import com.zoom.risk.platform.decision.po.TRule;
import com.zoom.risk.platform.decision.service.DecisionNodeService;
import com.zoom.risk.platform.decision.service.RiskDTreeService;
import com.zoom.risk.platform.decision.utils.DecisionTool;
import com.zoom.risk.platform.decision.vo.RouteMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/10.
 */
@Service("riskDTreeService")
public class RiskDTreeServiceImpl implements RiskDTreeService {
    private static final Logger logger = LogManager.getLogger(RiskDTreeServiceImpl.class);

    @Resource(name="shardingDataSource")
    private DataSource shardingDataSource;

    @Resource(name="riskDTreeMapper")
    private RiskDTreeMapper riskDTreeMapper;

    @Resource(name="decisionNodeService")
    private DecisionNodeService decisionNodeService;
    @Override
    @Transactional(readOnly = true)
    public List<DBNode> findNodesByPolicyId(Long policyId) {
        return riskDTreeMapper.findNodesByPolicyId(policyId);
    }


    @Transactional(readOnly = true)
    public DBNode buildDecisionTree(Long policyId){
        List<DBNode> list = riskDTreeMapper.findNodesByPolicyId(policyId);
        return decisionNodeService.buildDecisionTree(list);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> findDTPolicies() {
        return riskDTreeMapper.findDTPolicies();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findDTPolicy(String sceneNo) {
        return riskDTreeMapper.findDTPolicy(sceneNo);
    }

    @Transactional(readOnly = true)
    public List<TQuota> findDTQuota(){
        return riskDTreeMapper.findDTQuota();
    }

    @Transactional(readOnly = true)
    public List<TRule> mockDecisionTree2Rule(Long policyId){
        List<DBNode> allNodes = this.findNodesByPolicyId(policyId);
        List<TRule> allRules = buildDecisionTreeRules(policyId, allNodes.get(0).getSceneNo(), allNodes);
        this.mockDecisionTree(allRules);
        return allRules;
    }


    protected List<TRule> buildDecisionTreeRules(Long policyId, String sceneNo, List<DBNode> list){
        logger.info("List<DBNode> allNodes : {}", JSON.toJSONString(list));
        Map<Long, DBNode> temMap = new HashMap<>();
        list.forEach((node)->{
            temMap.put(node.getId(),node);
        });
        List<TRule> resultRules = new ArrayList<>();
        List<List<RouteMode>> allRoutes = decisionNodeService.generateRoutes(list);
        logger.info("List<List<RouteMode>> allRoutes : {}", JSON.toJSONString(allRoutes));
        for( int i = 0; i < allRoutes.size(); i++ ){
            List<RouteMode> singleRoute = allRoutes.get(i);
            logger.info("List<RouteMode>: {}", JSON.toJSONString(singleRoute));
            RouteMode routeLast = singleRoute.get(0);
            DBNode routeLeafNode = temMap.get(routeLast.getNodeId());
            TRule rule = new TRule(policyId,sceneNo, singleRoute.size(), DecisionTool.getParamStr(singleRoute),routeLeafNode.getDecisionCode(), routeLeafNode.getActionCode(),routeLeafNode.getReason());
            String[] values = DecisionTool.getRouteExpression(singleRoute);
            rule.setRuleContent(values[0]);
            rule.setName(values[1]);
            rule.setDescription(values[2]);
            rule.setScore(DecisionTool.getRouteScore(singleRoute, temMap));
            resultRules.add(rule);
        }

        return resultRules;
    }

    protected void mockDecisionTree(List<TRule> list){
        list.forEach((rule)->{
            String ruleNo = "TR"+rule.getSceneNo()+"_0000000";
            rule.setRuleNo(ruleNo);
            rule.setStatus(2);
        });
        logger.info("List<Rule> allRules : " + JSON.toJSONString(list));
    }
}
