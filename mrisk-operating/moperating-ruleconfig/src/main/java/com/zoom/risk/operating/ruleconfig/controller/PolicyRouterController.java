package com.zoom.risk.operating.ruleconfig.controller;

import com.zoom.risk.operating.ruleconfig.model.PolicyRouter;
import com.zoom.risk.operating.ruleconfig.service.PolicyRouterService;
import com.zoom.risk.operating.ruleconfig.service.PolicyService;
import com.zoom.risk.operating.ruleconfig.service.SceneConfigService;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.operating.ruleconfig.utils.RouterTools;
import com.zoom.risk.operating.ruleconfig.vo.RouteMode;
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
 * Created by liyi8 on 2017/6/26.
 */
@Controller
@RequestMapping("/policyRouter")
public class PolicyRouterController {
    private static final Logger logger = LogManager.getLogger(PolicyRouterController.class);

    @Autowired
    private SceneConfigService sceneConfigService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private PolicyRouterService policyRouterService;

    @RequestMapping("/toEdit")
    public ModelAndView toEdit(@RequestParam("sceneNo") String sceneNo) {
        ModelAndView modelAndView = MvUtils.getView("/ruleconfig/PolicyRouter");
        List<PolicyRouter> routerList = policyRouterService.selectBySceneNo(sceneNo);
        RouterTools.fillRouteMode(routerList);
        if (!routerList.isEmpty()) {
            modelAndView.addObject("selectedAttribute", routerList.get(0).getRouteMode().getSelectedAttribute());
        }
        modelAndView.addObject("routerList", routerList);
        modelAndView.addObject("sceneNo", sceneNo);
        modelAndView.addObject("sceneVoList", sceneConfigService.getPolicyRouterVo(sceneNo));
        modelAndView.addObject("policyList", policyService.selectBySceneNo(sceneNo));
        return modelAndView;
    }

    @RequestMapping("/saveRouter")
    @ResponseBody
    public Map<String, Object> saveRouter(PolicyRouter policyRouter) {
        boolean ret = true;
        RouteMode routeMode = policyRouter.getRouteMode();
        if(!routeMode.getIsJoin()){
            routeMode.removeSecondRoute();
        }
        RouterTools.fillRouteExpression(policyRouter);
        try {
            if (policyRouter.getId() == null) {
                policyRouterService.insert(policyRouter);
            } else {
                policyRouterService.updateById(policyRouter);
            }
        } catch (Exception e) {
            logger.error(e);
            ret = false;
        }
        return MvUtils.formatJsonResult(ret);
    }

    @RequestMapping("/delRouter")
    @ResponseBody
    public Map<String, Object> delRouter(Long routerId) {
        if (routerId == null) {
            return MvUtils.formatJsonResult(false);
        }
        return MvUtils.formatJsonResult(policyRouterService.delById(routerId));
    }
}
