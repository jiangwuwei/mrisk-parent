package com.zoom.risk.operating.ruleconfig.controller;

import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.model.Rule;
import com.zoom.risk.operating.ruleconfig.model.RuleCondition;
import com.zoom.risk.operating.ruleconfig.service.*;
import com.zoom.risk.operating.ruleconfig.service.*;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/rule")
public class RuleController {

    private static final Logger logger = LogManager.getLogger(RuleController.class);

    @Autowired
    private RuleService ruleService;
    @Autowired
    private RuleConditionService ruleConditionService;
    @Autowired
    private QuotaService quotaService;
    @Autowired
    private ActionCodeDimService actionCodeDimService;
    @Autowired
    private SceneConfigService sceneConfigService;
    @Autowired
    private ExtendAttributeDimService extendAttributeDimService;

    @RequestMapping(value = {"/list-{sceneNo:\\S[^-]+}-{policyId:\\d+}-{offset:\\d+}-{limit:\\d+}", "/list-{sceneNo:\\S[^-]+}-{policyId:\\d+}"})
    public ModelAndView ruleList(@PathVariable String sceneNo, @PathVariable long policyId,
                                 @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        ModelAndView mView = MvUtils.getView("RuleList");
        LsManager.getInstance().check();
        mView.addObject("limit", limit);
        mView.addObject("offset", offset);
        mView.addObject("policyId", policyId);
        mView.addObject("sceneNo", sceneNo);
        int count = ruleService.selectCount(policyId);
        mView.addObject("totalPages", count / limit + 1);
        mView.addObject("list", ruleService.selectPage(policyId, offset, limit));
        return mView;
    }


    @RequestMapping("/insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(Rule rule, RuleCondition ruleCondition) {
        ResultCode resultCode = null;
        try {
            if (ruleService.existWeightValue(rule.getPolicyId(), rule.getWeightValue(), rule.getId())) {
                return MvUtils.formatJsonResult(new ResultCode(-1, "该策略下已经有同优先级的规则了"));
            }
            //不同模式，规则内容的处理方式不一样
            if (rule.getRuleMode() == 0) {
                ruleConditionService.formatRuleContent(rule, ruleCondition);
            } else {
                rule.setQuotaNoList(rule.getQuotas());
            }
            if (rule.getId() == null) {
                resultCode = ruleService.insert(rule);
            } else {
                resultCode = ruleService.update(rule);
                //返回更新后的rule content
                return MvUtils.formatJsonResult(resultCode, "ruleContent:" + rule.getRuleContent());
            }
        } catch (Exception e) {
            logger.error(e);
            resultCode = ResultCode.DB_ERROR;
        }
        return MvUtils.formatJsonResult(resultCode);
    }

    @RequestMapping("/checkRuleContent")
    @ResponseBody
    public Map<String, Object> checkRuleContent(Rule rule, RuleCondition ruleCondition) {
        try {
            if (ruleService.existWeightValue(rule.getPolicyId(), rule.getWeightValue(), rule.getId())) {
                return MvUtils.formatJsonResult(new ResultCode(-1, "该策略下已经有同优先级的规则了"));
            }
            //只能处理非sql模式
            if (rule.getRuleMode() == 0) {
                ruleConditionService.formatRuleContent(rule, ruleCondition);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("failed to check rule weight value,{}", rule);
        }
        return MvUtils.formatJsonResult(true, "ruleContent:" + rule.getRuleContent());
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestParam long id,
                                            @RequestParam int toStatus) {
        try {
            ruleService.updateStatus(id, toStatus, 1);
            return MvUtils.formatJsonResult(true);
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

    @RequestMapping("/delById")
    public Map<String, Object> delById(@RequestParam(value = "id", required = true) long id) {
        if (id <= 0) {
            return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
        }
        try {
            return MvUtils.formatJsonResult(ruleService.delById(id));
        } catch (Exception e) {
            logger.error(e);
        }
        return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
    }

    @RequestMapping("/getRuleDetail")
    public ModelAndView getRuleDetail(@RequestParam(value = "id", required = true) long id,
                                      @RequestParam(value = "ruleMode", required = true) int ruleMode,
                                      @RequestParam(value = "sceneNo", required = true) String sceneNo) {
        String pageString = ruleMode == 0 ? "EditRule" : "EditSqlRule";
        ModelAndView mView = MvUtils.getView("/ruleconfig/part/" + pageString);
        try {
            mView.addObject("sceneConfigList", sceneConfigService.getCondSceneConfVo(sceneNo));
            mView.addObject("extendAttributeList", extendAttributeDimService.selectAll());
            mView.addObject("sceneMoneyAttrList", sceneConfigService.getSceneNamePair(sceneNo, 4));
            mView.addAllObjects(actionCodeDimService.selectAll());
            mView.addObject("quotaList", quotaService.selectBySceneNo(sceneNo, Constants.STATUS_IN_EFFECT));
            mView.addObject("rule", id == 0 ? new Rule() : ruleService.selectById(id));
        } catch (Exception e) {
            logger.error(e);
        }
        return mView;
    }
}
