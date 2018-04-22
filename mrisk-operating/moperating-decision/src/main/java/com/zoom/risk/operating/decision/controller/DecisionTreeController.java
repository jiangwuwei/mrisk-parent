package com.zoom.risk.operating.decision.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.decision.common.DimConstants;
import com.zoom.risk.operating.decision.po.DBNode;
import com.zoom.risk.operating.decision.po.Node;
import com.zoom.risk.operating.decision.proxy.RiskDecisionTreeProxyService;
import com.zoom.risk.operating.decision.service.EchartsTreeVoService;
import com.zoom.risk.operating.decision.utils.RouteTools;
import com.zoom.risk.operating.decision.vo.EchartsTreeVo;
import com.zoom.risk.operating.decision.vo.Route;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by liyi8 on 2015/5/26.
 */
@Controller
@RequestMapping("/dt")
public class DecisionTreeController {

    private static final Logger logger = LogManager.getLogger(DecisionTreeController.class);

    @Resource(name = "riskDecisionTreeProxyService")
    private RiskDecisionTreeProxyService riskDecisionTreeProxyService;
    @Resource(name = "echartsTreeVoService")
    private EchartsTreeVoService echartsTreeVoService;

    @RequestMapping("/buildTree")
    @ResponseBody
    public Map<String, Object> buildTree(@RequestParam(required = false) Long policyId) {
        Map<String, Object> map = Maps.newHashMap();
        ResultCode res = ResultCode.SUCCESS;
        try {
            EchartsTreeVo vo = echartsTreeVoService.buildTree(policyId);
            map.put("echartsVoString", JSONObject.toJSONString(vo));
        } catch (Exception e) {
            logger.error(e);
            res = ResultCode.FAILED;
        }
        map.put("res", res);
        return map;
    }

    /**
     * build decision tree for event center
     *
     * @param policyId
     * @return
     */
    @RequestMapping("/buildEventTree")
    @ResponseBody
    public Map<String, Object> buildEventTree(@RequestParam Long policyId, @RequestParam String nodeIdStr) {
        Map<String, Object> map = Maps.newHashMap();
        ResultCode res = ResultCode.SUCCESS;
        try {
            if (StringUtils.isNotBlank(nodeIdStr)) {
                String[] nodeIdArr = nodeIdStr.split(",");
                List<String> nodeIdList = Arrays.asList(nodeIdArr);
                EchartsTreeVo vo = echartsTreeVoService.buildEventTree(policyId, nodeIdList);
                map.put("echartsVoString", JSONObject.toJSONString(vo));
            } else {
                logger.error("failed to build event center decision tree, node id string is blank, policyId:" + policyId);
                res = ResultCode.FAILED;
            }
        } catch (Exception e) {
            logger.error(e);
            res = ResultCode.FAILED;
        }
        map.put("res", res);
        return map;
    }

    @RequestMapping("/findNodesById")
    public ModelAndView findNodesByNodeId(@RequestParam(required = false) Long nodeId, @RequestParam String sceneNo,
                                          @RequestParam(required = false) Long branchParentId, @RequestParam(required = false) Integer nodeType) {
        ModelAndView modelAndView = MvUtils.getView("/decisiontree/part/DecisionTree");
        DBNode currNode = new DBNode();
        List<DBNode> list = Lists.newArrayList();
        if (nodeId != null) {
            list = riskDecisionTreeProxyService.findNodesByNodeId(nodeId);
            ListIterator<DBNode> iterator = list.listIterator();
            while (iterator.hasNext()) {
                DBNode node = iterator.next();
                if (nodeId.equals(node.getId())) {
                    currNode = node;
                    iterator.remove();
                    break;
                }
            }
        } else {
            currNode.setParentId(branchParentId);
            currNode.setNodeType(nodeType == null ? Node.NODE_TYPE_BRANCH : nodeType);
        }
        RouteTools.fillRouteMode(currNode);
        RouteTools.fillRouteMode(list);
        modelAndView.addObject("list", list);
        modelAndView.addObject("currNode", currNode);
        modelAndView.addObject("quotaList", riskDecisionTreeProxyService.findQuotaByScene(sceneNo));
        modelAndView.addObject("actionCodeList", riskDecisionTreeProxyService.findDimByCode(DimConstants.ACTION_CODE));
        modelAndView.addObject("refuseCodeList", riskDecisionTreeProxyService.findDimByCode(DimConstants.REFUSE_CODE));
        return modelAndView;
    }

    @RequestMapping("/saveNode")
    @ResponseBody
    public Map<String, Object> saveNode(DBNode dbNode, @RequestParam(name = "updateType", required = false) Integer updateType,
                                        @RequestParam(name = "branchParentId", required = false) Long branchParentId) {
        Boolean ret = true;
        try {
            this.formatRoute(dbNode);
            if (dbNode.getId() == null) {
                this.formatNode(dbNode);
                ret = riskDecisionTreeProxyService.saveNode(dbNode, branchParentId);
            } else {
                if (updateType == null) {
                    return MvUtils.formatJsonResult(false);
                }
                ret = riskDecisionTreeProxyService.updateNode(dbNode, updateType);
            }
        } catch (Exception e) {
            logger.error(e);
            ret = false;
        }
        return MvUtils.formatJsonResult(ret);
    }

    @RequestMapping("/delNodeById")
    @ResponseBody
    public Map<String, Object> delNodeById(@RequestParam Long nodeId) {
        boolean retBoolean = true;
        try {
            int ret = riskDecisionTreeProxyService.delNodeByNodeId(nodeId);
            retBoolean = ret > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e);
            retBoolean = false;
        }
        return MvUtils.formatJsonResult(retBoolean);
    }

    /**
     * remove unnecessary node information
     *
     * @param dbNode
     */
    private void formatNode(DBNode dbNode) {
        if (dbNode.getNodeType() == Node.NODE_TYPE_BRANCH) {
            this.removeLeafNodeInfo(dbNode);
        }
        if (dbNode.getNodeType() == Node.NODE_TYPE_LEAF) {
            this.removeBranchNodeInfo(dbNode);
        }
    }

    /**
     * delete invalid route info and fill chinese name, param name, route name
     *
     * @param dbNode
     */
    private void formatRoute(DBNode dbNode) {
        if (dbNode.getRouteMode() != null) {
            List<Route> routes = dbNode.getRouteMode().getRoutes();
            if (routes != null && !routes.isEmpty()) {
                int count = 0;
                ListIterator<Route> iterator = routes.listIterator();
                while (iterator.hasNext()) {
                    Route route = iterator.next();
                    //delete invalid condition
                    if (!dbNode.getRouteMode().getIsJoin() && count == 1) {
                        iterator.remove();
                        break;
                    }
                    route.setChineseName(dbNode.getChineseName());
                    route.setParamName(dbNode.getParamName());
                    route.setRouteName(dbNode.getRouteName());
                    count++;
                }
            }
            RouteTools.fillRouteExpression(dbNode);
        }
    }

    /**
     * remove branch node info when saving or updating branch node
     *
     * @param dbNode
     */
    private void removeBranchNodeInfo(DBNode dbNode) {
        dbNode.setChineseName(null);
        dbNode.setParamName(null);
        dbNode.setQuotaId(null);
    }

    /**
     * remove leaf node info when saving or updating leaf node
     *
     * @param dbNode
     */
    private void removeLeafNodeInfo(DBNode dbNode) {
        dbNode.setActionCode(null);
        dbNode.setDecisionCode(null);
        dbNode.setDescription(null);
        dbNode.setReason(null);
    }

    @RequestMapping("/nodeList")
    public ModelAndView nodeList(@RequestParam(name = "policyId", required = true) Long policyId) {
        ModelAndView modelAndView = MvUtils.getView("/decisiontree/part/NodeList");
        List<DBNode> nodes = riskDecisionTreeProxyService.findNodesByPolicyId(policyId);
        nodes.forEach(node -> {
            node.setRouteExpression(RouteTools.getRouteMode(node.getRouteExpression()).getMevlExpression());
        });
        modelAndView.addObject("list", nodes);
        return modelAndView;
    }
}
