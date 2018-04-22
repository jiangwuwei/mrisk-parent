package com.zoom.risk.operating.scard.controller;

import com.alibaba.fastjson.JSONObject;
import com.zoom.risk.operating.ruleconfig.service.SceneConfigService;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.operating.ruleconfig.vo.RouteMode;
import com.zoom.risk.operating.scard.model.SCardRouter;
import com.zoom.risk.operating.scard.service.SCardService;
import com.zoom.risk.operating.scard.service.SCardRouterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller
@RequestMapping("/scardRouter")
public class SCardRouterController {
    private static final Logger logger = LogManager.getLogger(SCardRouterController.class);

    @Autowired
    private SceneConfigService sceneConfigService;
    @Autowired
    private SCardService scardDefinitionService;
    @Autowired
    private SCardRouterService scardRouterService;

    public static void fillRouteMode(List<SCardRouter> routerList) {
        for (SCardRouter router : routerList) {
            router.setRouteMode(getRouteMode(router.getRouterExpressionJson()));
        }
    }

    public static RouteMode getRouteMode(String routeExpressionJson) {
        if (StringUtils.isEmpty(routeExpressionJson)) {
            return new RouteMode();
        }
        return JSONObject.parseObject(routeExpressionJson, RouteMode.class);
    }

    @RequestMapping("/toEdit")
    public ModelAndView toEdit(@RequestParam("sceneNo") String sceneNo) {
        ModelAndView modelAndView = MvUtils.getView("/scard/SCardRouter");
        List<SCardRouter> routerList = scardRouterService.selectBySceneNo(sceneNo);
        fillRouteMode(routerList);
        if (!routerList.isEmpty()) {
            modelAndView.addObject("selectedAttribute", routerList.get(0).getRouteMode().getSelectedAttribute());
        }
        modelAndView.addObject("routerList", routerList);
        modelAndView.addObject("sceneNo", sceneNo);
        modelAndView.addObject("sceneVoList", sceneConfigService.getPolicyRouterVo(sceneNo));
        modelAndView.addObject("policyList", scardDefinitionService.selectBySceneNo(sceneNo));
        return modelAndView;
    }

    @RequestMapping("/saveScardRouter")
    @ResponseBody
    public Map<String, Object> saveRouter(SCardRouter policyRouter) {
        boolean ret = true;
        RouteMode routeMode = policyRouter.getRouteMode();
        if(!routeMode.getIsJoin()){
            routeMode.removeSecondRoute();
        }
        fillRouteExpression(policyRouter);
        try {
            if (policyRouter.getId() == null) {
                scardRouterService.insert(policyRouter);
            } else {
                scardRouterService.updateById(policyRouter);
            }
        } catch (Exception e) {
            logger.error(e);
            ret = false;
        }
        return MvUtils.formatJsonResult(ret);
    }

    public static void fillRouteExpression(SCardRouter router) {
        if (router.getRouteMode() != null) {
            router.setRouterExpressionJson(JSONObject.toJSONString(router.getRouteMode()));
            router.setRouterExpression(router.getRouteMode().getMevlExpression());
        }
    }



    @RequestMapping("/delScardRouter")
    @ResponseBody
    public Map<String, Object> delRouter(Long routerId) {
        if (routerId == null) {
            return MvUtils.formatJsonResult(false);
        }
        return MvUtils.formatJsonResult(scardRouterService.delById(routerId));
    }
}
