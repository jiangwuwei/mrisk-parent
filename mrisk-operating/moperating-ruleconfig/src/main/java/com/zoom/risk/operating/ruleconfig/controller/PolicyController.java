package com.zoom.risk.operating.ruleconfig.controller;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.model.Policy;
import com.zoom.risk.operating.ruleconfig.service.*;
import com.zoom.risk.operating.ruleconfig.service.*;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    private static final Logger logger = LogManager.getLogger(PolicyController.class);

    @Autowired
    private PolicyService policyService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private QuotaService quotaService;
    @Autowired
    private ActionCodeDimService actionCodeDimService;
    @Autowired
    private SceneConfigService sceneConfigService;
    @Autowired
    private ExtendAttributeDimService extendAttributeDimService;
    @Autowired
    private PolicyRouterService policyRouterService;

    /**
     * 跳转到编辑页面
     *
     * @param sceneNo  场景号
     * @param policyId 默认为0，表示新增
     * @return
     */
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(@RequestParam(required = true) String sceneNo,
                               @RequestParam(defaultValue = "0", required = false) long policyId,
                               @RequestParam(defaultValue = "", required = false) String ruleName,
                               @RequestParam(defaultValue = "0", required = false) int editTab) {
        ModelAndView mView = MvUtils.getView("/ruleconfig/PolicyCenter");
        ResultCode resultCode = ResultCode.SUCCESS;
        LsManager.getInstance().check();
        if (policyId == 0) {//新增
            Policy policy = new Policy();
            policy.setSceneNo(sceneNo);
            policy.setStatus(Constants.STATUS_DRAFT);
            policy.setPolicyPattern(Constants.POLICY_PATTERN_SCORE);
            mView.addObject("policy", policy);
            mView.addObject("ruleList", Lists.newArrayList());
        } else {//更新
            Policy policy = policyService.selectById(policyId);
            if (policy == null) {
                resultCode = ResultCode.NO_DATA;
            } else {
                mView.addObject("policy", policy);
                if (StringUtils.isBlank(ruleName)) {
                    mView.addObject("ruleList", ruleService.selectByPolicyId(policyId));
                } else {//搜索策略下的规则名称
                    mView.addObject("ruleList", ruleService.selectByNamePolicyId(policyId, ruleName));
                }
            }
        }
        mView.addObject("sceneConfigList", sceneConfigService.getCondSceneConfVo(sceneNo));
        mView.addObject("extendAttributeList", extendAttributeDimService.selectAll());
        mView.addObject("sceneMoneyAttrList", sceneConfigService.getSceneNamePair(sceneNo, 4));
        mView.addAllObjects(actionCodeDimService.selectAll());
        mView.addObject("quotaList", quotaService.selectBySceneNo(sceneNo, Constants.STATUS_IN_EFFECT));
        mView.addObject("res", resultCode);
        mView.addObject("ruleName", ruleName);
        mView.addObject("editTab", editTab);
        return mView;
    }

    @RequestMapping("/insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(Policy policy) {
        policy.setWeightGrade("[" + policy.getMin().intValue() + "," + policy.getMax().intValue() + "]");
        try {
            if (policyService.exists(policy.getSceneNo(), policy.getWeightValue(), policy.getId(), policy.getName())) {
                return MvUtils.formatJsonResult(new ResultCode(-1, "该场景" + policy.getSceneNo() + "下已经存在相同执行顺序的策略，或有相同名称的策略"));
            }
            if (policy.getId() == null) {
                return MvUtils.formatJsonResult(policyService.insert(policy), "policyId:" + policy.getId(), "sceneNo:" + policy.getSceneNo());
            }
        } catch (Exception e) {
            logger.error(e);
            return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
        }
        return MvUtils.formatJsonResult(policyService.update(policy), "policyId:" + policy.getId(), "sceneNo:" + policy.getSceneNo());
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestParam long id,
                                            @RequestParam int toStatus) {
        if (id <= 0 || toStatus < 1) {
            return MvUtils.formatJsonResult(ResultCode.ILLEAGE_PARAMS);
        }
        if ((toStatus == Constants.STATUS_DISCARD || toStatus == Constants.STATUS_DRAFT) && policyRouterService.existPolicy(id)) {
    		return MvUtils.formatJsonResult(new ResultCode(-10, "策略被策略路由引用，不能删除！"));
        }
        try {
            policyService.updateStatus(id, toStatus);
            return MvUtils.formatJsonResult(ResultCode.SUCCESS);
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

        return MvUtils.formatJsonResult(policyService.delById(id));
    }
}
