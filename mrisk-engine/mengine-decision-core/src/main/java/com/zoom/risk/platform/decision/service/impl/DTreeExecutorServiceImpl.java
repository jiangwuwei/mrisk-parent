package com.zoom.risk.platform.decision.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.platform.common.util.Utils;
import com.zoom.risk.platform.decision.cache.DTreeCacheService;
import com.zoom.risk.platform.decision.exception.InvalidNodeException;
import com.zoom.risk.platform.decision.exception.InvalidQuotaException;
import com.zoom.risk.platform.decision.exception.MvelExecutionException;
import com.zoom.risk.platform.decision.exception.NoValidRouteFoundException;
import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.Node;
import com.zoom.risk.platform.decision.po.TQuota;
import com.zoom.risk.platform.decision.service.DTreeExecutorService;
import com.zoom.risk.platform.decision.service.QuotaQueryHelpService;
import com.zoom.risk.platform.decision.vo.QutoaInfo;
import com.zoom.risk.platform.decision.vo.RouteMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/16.
 */
@Service("dtreeExecutorService")
public class DTreeExecutorServiceImpl implements DTreeExecutorService {
    private static final Logger logger = LogManager.getLogger(DTreeExecutorServiceImpl.class);

    @Resource(name="dtreeCacheService")
    private DTreeCacheService dtreeCacheService;

    @Resource(name="quotaQueryHelpService")
    private QuotaQueryHelpService quotaQueryHelpService;
    /**
     * @param node        the root node for decision tree which represent the decision tree
     * @param context     the request context
     * @param routes      represent all returned execution
     * @param quotaValueList  name pairs for all quotas
     * @param scene4no    the sceneNo with first 4 digital
     */
    public  void executeDecisionTree(DBNode node, Map<String,Object> context, List<DBNode> routes, List<QutoaInfo> quotaValueList, String scene4no){
        routes.add(node);
        if ( node.getNodeType() != Node.NODE_TYPE_LEAF ) {
            String paramName = node.getParamName();
            Long takingTime = 0L;
            Long quotaId = null;
            Object value = context.get(paramName);
            if ( Utils.isEmpty(value) ) {
                TQuota quota = dtreeCacheService.getQuota(scene4no, paramName);
                if ( quota != null ) {
                    quotaId = quota.getId();
                    if (quota.getSourceType() == TQuota.SOURCE_TYPE_1) {
                        throw new InvalidQuotaException("Can not find value in request context for paramName [" + quota.getParamName() + "," + quota.getQuotaNo() + "]");
                    } else {
                        long time = System.currentTimeMillis();
                        value = quotaQueryHelpService.getQuotaValue(quota,context);
                        takingTime = System.currentTimeMillis() - time;
                        context.put(paramName, value);
                    }
                }else{
                    throw new InvalidQuotaException("Can not find quota according to the paramName:" + paramName + ", sceneNo:" + scene4no);
                }
            }
            quotaValueList.add(new QutoaInfo( quotaId, paramName, String.valueOf(value), takingTime) );
            boolean atLeastOneRoute = false;
            if ( node.getChildrens() != null && node.getChildrens().size() > 0 ) {
                for (DBNode child : node.getChildrens()) {
                    //convert json string to java Object
                    RouteMode model = JSON.parseObject(child.getRouteExpression(), new TypeToken<RouteMode>() {}.getType());
                    boolean hitRoute = false;
                    try {
                        hitRoute = MVEL.evalToBoolean(model.getMevlExpression(), context);
                    } catch (Exception e) {
                        throw new MvelExecutionException("Mvel execution happens error", e.getCause());
                    }
                    if (hitRoute) {
                        atLeastOneRoute = true;
                        executeDecisionTree(child, context, routes, quotaValueList, scene4no);
                        break;
                    }
                }
            } else {
                throw new InvalidNodeException("The non-leaf node should have childrens, nodeNo: " + node.getNodeNo());
            }
            if ( !atLeastOneRoute ) {
                String nodesStr = JSON.toJSONString(routes, SerializerFeature.SkipTransientField, SerializerFeature.DisableCircularReferenceDetect);
                String errorMessage = "Can not find the right path for this node : " + node.getNodeNo() + ", info :[" + nodesStr + "]";
                logger.error(errorMessage);
                throw new NoValidRouteFoundException(errorMessage);
            }
        }
    }
}
