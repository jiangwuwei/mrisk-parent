package com.zoom.risk.platform.decision.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.platform.decision.exception.InvalidNodeException;
import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.Node;
import com.zoom.risk.platform.decision.service.DecisionNodeService;
import com.zoom.risk.platform.decision.vo.RouteMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiangyulin on 2017/5/16.
 */
@Service("decisionNodeService")
public class DecisionNodeServiceImpl implements DecisionNodeService {
    private static final Logger logger = LogManager.getLogger(DecisionNodeServiceImpl.class);
    /**
     * 检查各个节点是否符合要求
     * @param treeNodes
     */
    protected void pureValidation(List<DBNode> treeNodes){
        Map<Long,DBNode> resultMap = new HashMap<>();
        treeNodes.forEach((node)->{
            resultMap.put(node.getParentId(), node);
        });
        AtomicInteger rootCount = new AtomicInteger(0);
        AtomicInteger leafCount = new AtomicInteger(0);
        treeNodes.forEach((nd)->{
            DBNode node = resultMap.get(nd.getId());
            if ( node == null ){
                //leaf node
                if ( nd.getNodeType() != DBNode.NODE_TYPE_LEAF || isEmpty(nd.getDecisionCode()) || isEmpty(nd.getScore())
                        || isEmpty(nd.getRouteExpression()) || isEmpty(nd.getRouteName())  ){
                    throw new InvalidNodeException("叶子节点验证发生错误, nodeNo:" + nd.getNodeNo());
                }
                //should find the parent Node
                if  ( resultMap.get(nd.getParentId()) == null ){
                    throw new InvalidNodeException("叶子节点找不到父节点错误, nodeNo:" + nd.getNodeNo());
                }
                leafCount.incrementAndGet();
            }else if ( nd.getId().equals(nd.getParentId())){
                //root node
                if ( isEmpty(nd.getParamName() )  ||  nd.getNodeType() != Node.NODE_TYPE_ROOT ){
                    throw new InvalidNodeException("根节点验证发生错误, nodeNo:" + nd.getNodeNo());
                }
                rootCount.incrementAndGet();
            }else{
                //branch node
                if ( isEmpty(nd.getParamName()) || isEmpty(nd.getScore()) || isEmpty(nd.getRouteName())   ||  nd.getNodeType() != Node.NODE_TYPE_BRANCH  ){
                    throw new InvalidNodeException("枝干节点验证发生错误, nodeNo:" + nd.getNodeNo());
                }
            }
        });
        if ( rootCount.intValue() != 1 ){
            throw new InvalidNodeException("根节点的个数不对, 根节点总数 count:" + rootCount.intValue() );
        }
        if ( leafCount.intValue() < 1 ){
            throw new InvalidNodeException("叶子节点的个数不对, 叶节点总数 count:" + leafCount.intValue() );
        }
    }
    /**
     * 将叶子节点以及枝干节点的路劲表达式转换成RouteMode对象
     * @param list
     */
    private void initRouteMode(List<DBNode> list){
        list.forEach((node)->{
            String expression = node.getRouteExpression();
            if ( expression != null && (!"".equals(expression)) ){
                RouteMode model = JSON.parseObject(expression,new TypeToken<RouteMode>(){}.getType());
                node.setRouteMode(model);
            }
        });
    }

    /**
     * 转换成 Map 并保留原始的数据集
     * @param list
     * @return
     */
    private Map<Long,DBNode> transform2Map(List<DBNode> list){
        Map<Long,DBNode> resultMap = new HashMap<>();
        list.forEach((node)->{
            resultMap.put(node.getId(), node);
        });
        return resultMap;
    }

    /**
     * 根据原始数据返回所有的叶子节点
     */
    private List<DBNode> findAllLeafNode(List<DBNode> list){
        Map<Long,String> resultMap = new HashMap<>();
        list.forEach((node)->{
            resultMap.put(node.getParentId(), "1");
        });
        List<DBNode> resultList = new ArrayList<>();
        list.forEach((node)->{
            if ( resultMap.get(node.getId()) == null) {
                resultList.add(node);
            }
        });
        return resultList;
    }

    /**
     * 构建只有一个根节点的决策树
     * 1. 如果有节点找不到父节点
     * 2. 如果最后返回多个Root节点
     * 报异常，数据格式问题
     * @param list
     * @return Root Node
     */
    private DBNode validateAndBuildTree(List<DBNode> list){
        Map<Long,DBNode> resultMap = new HashMap<>();
        list.forEach((node)->{
            resultMap.put(node.getId(), node);
        });
        Iterator<DBNode> it = list.iterator();
        while ( it.hasNext() ){
            DBNode node = it.next();
            Long id = node.getId();
            if ( ! id.equals(node.getParentId()) ){
                DBNode parent = resultMap.get( node.getParentId() );
                if ( parent == null ){
                    throw new InvalidNodeException("存在节点找不到父节点的, nodeNo : " +  node.getNodeNo());
                }else{
                    parent.addChild(node);
                    it.remove();
                }
            }
        }
        if ( list.size() != 1 ){
            throw new InvalidNodeException("树根节点个数不对, 总个数: " +  list.size() );
        }
        return list.get(0);
    }

    /**
     * 反向遍历迭代
     * @param node
     * @param parentMap
     * @param result
     */
    private void findUntilRoot(DBNode node, Map<Long,DBNode> parentMap, List<RouteMode> result){
        DBNode parentNode = parentMap.get(node.getParentId());
        RouteMode mode = node.getRouteMode();
        mode.setNodeId(node.getId());
        result.add(mode);
        if (  parentNode != null &&  (!parentNode.getId().equals(parentNode.getParentId())) ){
            findUntilRoot(parentNode, parentMap, result);
        }
    }

    /**
     * 暴露对外的接口
     * @param list
     * @return
     */
    public List<List<RouteMode>> generateRoutes(List<DBNode> list){
        //0.对各个节点的要求进行验证
        pureValidation(list);
        //1.初始化转换
        initRouteMode(list);
        //2.转换你成Map并保留原始数据
        Map<Long,DBNode> originaMap = transform2Map(list);
        //3.找出所有叶子节点
        List<DBNode> leafList = findAllLeafNode(list);
        logger.info("List<DBNode> leafList : {}", JSON.toJSONString(leafList));
        //4.构建只要一个根节点的决策树
        validateAndBuildTree(list);
        logger.info("List<DBNode> treeList : {}", JSON.toJSONString(list));
        //5.根据叶子节点反向回溯到根节点
        List<List<RouteMode>> resultList = new ArrayList<>();
        for( int i =0 ; i < leafList.size(); i++ ){
            DBNode node = leafList.get(i);
            List<RouteMode> oneRoute = new ArrayList<>();
            resultList.add(oneRoute);
            findUntilRoot(node, originaMap, oneRoute );
        }
        //按照规则个数排序
        Collections.sort(resultList, (list1,list2)->{
            return list1.size()-list2.size();
        });
        return resultList;
    }

    public DBNode buildDecisionTree(List<DBNode> list){
        //0.对各个节点的要求进行验证
        pureValidation(list);
        //1.初始化转换
        initRouteMode(list);
        //2.构建只要一个根节点的决策树
        return validateAndBuildTree(list);
    }

    protected boolean isEmpty(Object str){
        if ( str == null){
            return true;
        }else if ( "null".equals( String.valueOf(str)) || "".equals( String.valueOf(str))){
            return true;
        }else{
            return false;
        }
    }
}
