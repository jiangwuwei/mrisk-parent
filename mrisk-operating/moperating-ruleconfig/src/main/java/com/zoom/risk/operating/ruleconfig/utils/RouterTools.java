package com.zoom.risk.operating.ruleconfig.utils;

import com.alibaba.fastjson.JSONObject;
import com.zoom.risk.operating.ruleconfig.model.PolicyRouter;
import com.zoom.risk.operating.ruleconfig.vo.RouteMode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by liyi8 on 2017/5/11.
 */
public class RouterTools {

    private static final Logger logger = LogManager.getLogger(RouterTools.class);

    public static void fillRouteExpression(PolicyRouter router) {
        if (router.getRouteMode() != null) {
            router.setRouterExpressionJson(JSONObject.toJSONString(router.getRouteMode()));
            router.setRouterExpression(router.getRouteMode().getMevlExpression());
        }
    }

    public static void fillRouteMode(List<PolicyRouter> routerList) {
        for (PolicyRouter router : routerList) {
            router.setRouteMode(getRouteMode(router.getRouterExpressionJson()));
        }
    }

    public static void fillCardRouteMode(List<PolicyRouter> routerList) {
        for (PolicyRouter router : routerList) {
            router.setRouteMode(getRouteMode(router.getRouterExpressionJson()));
        }
    }

    public static void fillRouteMode(PolicyRouter router) {
        if (router != null) {
            router.setRouteMode(getRouteMode(router.getRouterExpressionJson()));
        }
    }

    public static RouteMode getRouteMode(String routeExpressionJson) {
        if (StringUtils.isEmpty(routeExpressionJson)) {
            return new RouteMode();
        }
        return JSONObject.parseObject(routeExpressionJson, RouteMode.class);
    }
}
