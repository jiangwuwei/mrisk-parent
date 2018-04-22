package com.zoom.risk.operating.decision.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zoom.risk.operating.decision.po.DBNode;
import com.zoom.risk.operating.decision.po.Node;
import com.zoom.risk.operating.decision.service.EchartsTreeVoService;
import com.zoom.risk.operating.decision.service.RiskDecisionTreeService;
import com.zoom.risk.operating.decision.vo.EchartsTreeVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.zoom.risk.operating.decision.common.Constants.DECISION_CODE_PASS;
import static com.zoom.risk.operating.decision.common.Constants.DECISION_CODE_VERIFY;

/**
 * Created by liyi8 on 2017/5/30.
 */
@Service("echartsTreeVoService")
public class EchartsTreeVoServiceImpl implements EchartsTreeVoService {
    private static final Logger logger = LogManager.getLogger(EchartsTreeVoServiceImpl.class);

    public static final String SYMBOL_EMPTY_CIRCLE = "emptyCircle";
    //    public static final String SYMBOL_DECLINE = "image://https://jr.letv.com/a1/M00/1B/08/CjwsYlknifiAG0nBAAABjbdav1c599.png";
//    public static final String SYMBOL_PASS = "image://https://jr.letv.com/a1/M00/1A/F8/CjwsU1kniaKADhw0AAABxlnl1_4733.png";
    public static final String SYMBOL_DECLINE = "../src/images/no.png";
    public static final String SYMBOL_PASS = "../src/images/yes.png";
    public static final String SYMBOL_VERIFY = "../src/images/verify.png";
    public static final int SYMBOL_SIZE_LEAF = 1;
    public static final int SYMBOL_SIZE_BRANCH = 0;

    @Resource(name = "riskDecisionTreeService")
    private RiskDecisionTreeService riskDecisionTreeService;

    @Override
    public EchartsTreeVo buildTree(Long policyId) {
        DBNode dbNode = null;
        try {
            dbNode = riskDecisionTreeService.buildDecisionTree(policyId, false);
        } catch (Exception e) {
            logger.error(e);
            logger.error("failed to build decision tree, policyId:{}", policyId);
        }
        if (dbNode == null) {
            EchartsTreeVo vo = new EchartsTreeVo();
            vo.setName("点击设置根节点");
            vo.setChildren(Lists.newArrayList());
            vo.setItemStyle(this.getRootBranchPointItemStyle(null));
            return vo;
        }
        return this.buildVo(dbNode);
    }

    @Override
    public EchartsTreeVo buildEventTree(Long policyId, List<String> hitNodeIdList) {
        DBNode dbNode = null;
        try {
            dbNode = riskDecisionTreeService.buildDecisionTree(policyId, hitNodeIdList);
        } catch (Exception e) {
            logger.error(e);
            logger.error("failed to build decision tree, policyId:{}", policyId);
        }
        if (dbNode == null) {
            return null;
        }
        return this.buildVo(dbNode);
    }

    private EchartsTreeVo buildVo(DBNode dbNode) {
        if (dbNode.getNodeType() == Node.NODE_TYPE_LEAF) {
            return this.processLeafNode(dbNode);
        }
        EchartsTreeVo vo = new EchartsTreeVo();
        //root or branch point?
        if (dbNode.getNodeType() == Node.NODE_TYPE_ROOT || dbNode.getNodeType() == Node.NODE_TYPE_BRANCH) {
            this.processRootBranchNode(vo, dbNode);
            vo.setName(dbNode.getChineseName());
        }
        List<EchartsTreeVo> children = Lists.newArrayList();
        dbNode.getChildrens().forEach((childNode) -> {
            if (childNode.getNodeType() == Node.NODE_TYPE_LEAF) {
                children.add(this.processLeafNode(childNode));
            }
            if (childNode.getNodeType() == Node.NODE_TYPE_BRANCH) {
                EchartsTreeVo branchPathVo = new EchartsTreeVo();
                this.processBranchPathNode(branchPathVo, childNode);
                children.add(branchPathVo);
                List<EchartsTreeVo> branchChildren = Lists.newArrayList();
                branchChildren.add(this.buildVo(childNode));
                branchPathVo.setChildren(branchChildren);
            }
        });
        vo.setChildren(children);
        return vo;
    }

    private EchartsTreeVo processLeafNode(DBNode leafNode) {
        EchartsTreeVo childVo = new EchartsTreeVo();
        //for echarts path node
        List<EchartsTreeVo> children = Lists.newArrayList();
        childVo.setChildren(children);
        this.processBranchPathNode(childVo, leafNode);
        //for echarts leaf node
        EchartsTreeVo leafChildVo = new EchartsTreeVo();
        leafChildVo.setParentId(leafNode.getParentId());
        leafChildVo.setId(leafNode.getId());
        leafChildVo.setNodeType(Node.NODE_TYPE_LEAF);
        leafChildVo.setSymbol(this.getSymbol(leafNode.getDecisionCode()));
        children.add(leafChildVo);
        return childVo;
    }

    private EchartsTreeVo processRootBranchNode(EchartsTreeVo rootBranchVo, DBNode rootBranchNode) {
        rootBranchVo.setName(rootBranchNode.getRouteName());
        rootBranchVo.setId(rootBranchNode.getId());
        rootBranchVo.setParentId(rootBranchNode.getParentId());
        rootBranchVo.setNodeType(rootBranchNode.getNodeType());
        rootBranchVo.setItemStyle(this.getRootBranchPointItemStyle(rootBranchNode));
        return rootBranchVo;
    }

    private EchartsTreeVo processBranchPathNode(EchartsTreeVo branchPathVo, DBNode branchPathNode) {
        branchPathVo.setName(branchPathNode.getRouteName());
        branchPathVo.setId(branchPathNode.getId());
        branchPathVo.setParentId(branchPathNode.getParentId());
        branchPathVo.setNodeType(branchPathNode.getNodeType());
        branchPathVo.setItemStyle(this.getBranchPathItemStyle(branchPathNode)); //special item style
        branchPathVo.setSymbol(SYMBOL_EMPTY_CIRCLE);
        branchPathVo.setSymbolSize(this.getSymbolSize(branchPathNode.getNodeType()));
        return branchPathVo;
    }

    private Map<String, Object> getRootBranchPointItemStyle(DBNode dbNode) {
        //normal
        Map<String, Object> label = Maps.newHashMap();
        label.put("show", true);
        label.put("position", "top");
        this.decorateHitNode(label, dbNode);
        Map<String, Object> labelLine = Maps.newHashMap();
        labelLine.put("length", 1);
        Map<String, Object> normal = Maps.newHashMap();
        normal.put("label", label);
        normal.put("labelLine", labelLine);
        normal.put("color", "#FFFFFF");
        normal.put("borderWidth", 1);
        normal.put("borderColor", "#BFBFBF");
        //emphasis
        Map<String, Object> emphasis = Maps.newHashMap();
        emphasis.put("borderWidth", 2);
        emphasis.put("color", "#FFFFFF");
        emphasis.put("borderColor", "#5C94EF");
        emphasis.put("shadowColor", "#98BFFF");
        emphasis.put("shadowBlur", 0);
        emphasis.put("shadowOffsetX", 3);
        emphasis.put("shadowOffsetY", 5);
        //item style
        Map<String, Object> itemStyle = Maps.newHashMap();
        itemStyle.put("normal", normal);
        itemStyle.put("emphasis", emphasis);
        return itemStyle;
    }

    private Map<String, Object> getBranchPathItemStyle(DBNode dbNode) {
        //normal
        Map<String, Object> label = Maps.newHashMap();
        label.put("show", true);
        label.put("position", "top");
        this.decorateHitNode(label, dbNode);
        Map<String, Object> labelLine = Maps.newHashMap();
        labelLine.put("length", 1);
        Map<String, Object> normal = Maps.newHashMap();
        normal.put("label", label);
        normal.put("labelLine", labelLine);
        normal.put("color", "rgba(255, 255, 255, 0)");
        //emphasis
        Map<String, Object> emphasis = Maps.newHashMap();
        emphasis.put("borderWidth", 0);
        emphasis.put("color", "rgba(0, 0, 0, 0)");
        //item style
        Map<String, Object> itemStyle = Maps.newHashMap();
        itemStyle.put("normal", normal);
        itemStyle.put("emphasis", emphasis);
        return itemStyle;
    }

    /**
     * set label text color to orange for hit node
     *
     * @param label
     * @param dbNode
     */
    private void decorateHitNode(Map<String, Object> label, DBNode dbNode) {
        if (dbNode != null && dbNode.getIsHitNode() != null && dbNode.getIsHitNode() == DBNode.IS_HIT_NODE_TRUE) {
            Map<String, Object> textColor = Maps.newHashMap();
            textColor.put("color", "orange");
            label.put("textStyle", textColor);
        }
    }

    private String getSymbol(int decisionCode) {
        if (decisionCode == DECISION_CODE_PASS) {
            return SYMBOL_PASS;
        }
        if (decisionCode == DECISION_CODE_VERIFY) {
            return SYMBOL_VERIFY;
        }
        return SYMBOL_DECLINE;
    }

    private int getSymbolSize(int nodeType) {
        if (nodeType == Node.NODE_TYPE_LEAF) {
            return SYMBOL_SIZE_LEAF;
        }
        return SYMBOL_SIZE_BRANCH;
    }
}
