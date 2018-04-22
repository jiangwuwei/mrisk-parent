package com.zoom.risk.platform.decision.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.platform.common.util.Utils;
import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.QuotaComponent;
import com.zoom.risk.platform.decision.vo.Route;
import com.zoom.risk.platform.decision.vo.RouteMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/24.
 */
public class DecisionTool {
    private static final Logger logger = LogManager.getLogger(DecisionTool.class);

    public static List<QuotaComponent> buildQuotaComponet(String requestParam) {
        List<QuotaComponent> list = null;
        if (Utils.isNotEmpty(requestParam)) {
            try {
                list = JSON.parseObject(requestParam, new TypeToken<ArrayList<QuotaComponent>>(){}.getType());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return list;
    }


    public static String getParamStr(List<RouteMode> list){
        StringBuffer buffer = new StringBuffer();
        for(int i=list.size()-1; i >=0 ; i-- ){
            RouteMode mode = list.get(i);
            if ( i != 0 ) {
                buffer.append(mode.getRoutes().get(0).getParamName() + ",");
            }else{
                buffer.append(mode.getRoutes().get(0).getParamName());
            }
        }
        return  buffer.toString();
    }

    public static String[] getRouteExpression(List<RouteMode> list){
        StringBuffer expression = new StringBuffer();
        StringBuffer routeName = new StringBuffer();
        StringBuffer ruleDesc = new StringBuffer();
        for(int i = list.size()-1; i >= 0 ; i-- ){
            RouteMode m = list.get(i);
            expression.append(m.getMevlExpression() +  " && ");
            Route condition = m.getRoutes().get(0);
            routeName.append(condition.getChineseName() + "[" + condition.getRouteName() + "]" +  "--");
            ruleDesc.append(condition.getParamName() + "[" + condition.getChineseName() + " : " + condition.getRouteName() + "]" +  "--");
        }
        int len = expression.length();
        expression.delete(len-4, len);

        int routeNameLen = routeName.length();
        routeName.delete(routeNameLen-2, routeNameLen);

        int ruleDescLen = ruleDesc.length();
        ruleDesc.delete(ruleDescLen-2, ruleDescLen);

        return new String[]{expression.toString(), routeName.toString(), ruleDesc.toString()};
    }

    public static Integer getRouteScore(List<RouteMode> list, Map<Long, DBNode> resultMap){
        Integer result = new Integer(0);
        for( int i = 0; i < list.size(); i++ ){
            DBNode node = resultMap.get(list.get(i).getNodeId());
            result = result + node.getScore();
        }
        return result;
    }
}
