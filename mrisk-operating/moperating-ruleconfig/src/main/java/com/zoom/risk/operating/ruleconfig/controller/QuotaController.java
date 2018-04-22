package com.zoom.risk.operating.ruleconfig.controller;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.model.Policies;
import com.zoom.risk.operating.ruleconfig.model.Quota;
import com.zoom.risk.operating.ruleconfig.model.QuotaStatisticsTemplate;
import com.zoom.risk.operating.ruleconfig.service.*;
import com.zoom.risk.operating.ruleconfig.service.*;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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

@Controller
@RequestMapping("/quota")
public class QuotaController {

    private static final Logger logger = LogManager.getLogger(QuotaController.class);

    @Autowired
    private QuotaService quotaService;
    @Autowired
    private QuotaDimService quotaDimService;
    @Autowired
    private RuleQuotaLinkService ruleQuotaLinkService;
    @Autowired
    private PoliciesService policiesService;
    @Autowired
    private QuotaTemplateService quotaTemplateService;
    @Autowired
    private SceneConfigService sceneConfigService;
    @Autowired
    private QuotaStatisticTemplateService quotaStatisticTemplateService;


    @RequestMapping("/list")
    public ModelAndView quotaList(@RequestParam(value = "sceneNo", required = false, defaultValue = "all") String sceneNo,
                                  @RequestParam(value = "quotaName", required = false, defaultValue = "") String quotaName) {
        ModelAndView mView = MvUtils.getView("/ruleconfig/QuotaCenter");
        LsManager.getInstance().check();
        List<Policies> policiesList = policiesService.selectAll();
        if (sceneNo.equals("all")) {
            sceneNo = policiesList.get(0) == null ? null : policiesList.get(0).getSceneNo();
        }
        List<Quota> quotaList = null;
        List<Pair<Policies, List<Quota>>> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(quotaName)) {//根据名称搜索
            quotaList = quotaService.selectByName(quotaName, 0, 0);
            mView.addObject("pageUrlPrex", "/quota/list?quotaName=" + quotaName);
        } else {
            quotaList = quotaService.selectPage(sceneNo, 0, 0);
            mView.addObject("pageUrlPrex", "/quota/list?sceneNo=" + sceneNo);
        }
        //一次查询所有指标与策略集，然后进行匹配
        Map<String, Policies> policiesMap = policiesService.getPoliciesMap(policiesList);
        list = quotaService.getPoliciesQuotaList(policiesMap, quotaList, sceneNo);

        mView.addObject("templateList", quotaTemplateService.selectAll());
        mView.addObject("list", list);
        mView.addObject("sceneNo", sceneNo);
        mView.addObject("quotaName", quotaName);
        mView.addObject("allPolicies", policiesList);
        mView.addAllObjects(quotaDimService.selectAll());
        mView.addObject("sceneConfigList", sceneConfigService.getCondSceneConfVo(sceneNo));
        mView.addObject("sceneMoneyAttrList", sceneConfigService.getSceneNamePair(sceneNo, 4));//金钱类属性
        return mView;
    }


    @RequestMapping("/getQuota")
    public ModelAndView quota(@RequestParam(value = "quotaId", required = true, defaultValue = "all") Long quotaId,
                              @RequestParam(value = "sceneNo", required = false, defaultValue = "all") String sceneNo,
                              @RequestParam(value = "quotaName", required = false, defaultValue = "") String quotaName) {
        ModelAndView mView = MvUtils.getView("/ruleconfig/part/QuotaDetail");
        List<Policies> policiesList = policiesService.selectAll();
        if (sceneNo.equals("all")) {
            sceneNo = policiesList.get(0) == null ? null : policiesList.get(0).getSceneNo();
        }
        Quota quota=null;
        if (quotaId==-1){
            quota = new Quota();
        }else{
            quota = quotaService.selectById(quotaId);
        }
        List<Quota> quotaList = null;
        List<Pair<Policies, List<Quota>>> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(quotaName)) {//根据名称搜索
            quotaList = quotaService.selectByName(quotaName, 0, 0);
            mView.addObject("pageUrlPrex", "/quota/list?quotaName=" + quotaName);
        } else {
            quotaList = quotaService.selectPage(sceneNo, 0, 0);
            mView.addObject("pageUrlPrex", "/quota/list?sceneNo=" + sceneNo);
        }
        //一次查询所有指标与策略集，然后进行匹配
        Map<String, Policies> policiesMap = policiesService.getPoliciesMap(policiesList);
        list = quotaService.getPoliciesQuotaList(policiesMap, quotaList, sceneNo);

        mView.addObject("templateList", quotaTemplateService.selectAll());
        mView.addObject("list", list);
        mView.addObject("quota", quota);
        mView.addObject("sceneNo", sceneNo);
        mView.addObject("allPolicies", policiesList);
        mView.addAllObjects(quotaDimService.selectAll());
        mView.addObject("sceneConfigList", sceneConfigService.getCondSceneConfVo(sceneNo));
        mView.addObject("sceneMoneyAttrList", sceneConfigService.getSceneNamePair(sceneNo, 4));//金钱类属性
        return mView;
    }

    @RequestMapping("/insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(Quota quota, QuotaStatisticsTemplate quotaStatisticsTemplate) {
        if (quota.getQuotaTemplateId() != null) {//使用了模板
            quota.setQuotaContent(quotaStatisticTemplateService.getQuota(quotaStatisticsTemplate, quota.getSceneNo(), quota.getQuotaTemplateId()));
            quota.setQuotaContentJson(new Gson().toJson(quotaStatisticsTemplate));
        }
        if (quota.getId() == null) {
            try {
                return MvUtils.formatJsonResult(quotaService.insert(quota));
            } catch (Exception e) {
                logger.error(e);
            }
            return MvUtils.formatJsonResult(false);
        }
        return MvUtils.formatJsonResult(quotaService.update(quota), "quotaContent:" + quota.getQuotaContent());
    }

    @RequestMapping("/checkQuotaContent")
    @ResponseBody
    public Map<String, Object> checkQuotaContent(Quota quota, QuotaStatisticsTemplate quotaStatisticsTemplate) {
        if (quota.getQuotaTemplateId() != null) {//使用了模板
            quota.setQuotaDataType(1);
            quota.setQuotaContent(quotaStatisticTemplateService.getQuota(quotaStatisticsTemplate, quota.getSceneNo(), quota.getQuotaTemplateId()));
            quota.setQuotaContentJson(new Gson().toJson(quotaStatisticsTemplate));
        }
        return MvUtils.formatJsonResult(true, "quotaContent:" + quota.getQuotaContent());
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestParam long id,
                                            @RequestParam int toStatus) {
        //废弃指标时，应当无规则引用
        if ((toStatus == Constants.STATUS_DISCARD) && ruleQuotaLinkService.existsQuota(id)) {
            return MvUtils.formatJsonResult(new ResultCode(-1, "废弃指标失败，存在规则引用该指标, id:" + id));
        }
        return MvUtils.formatJsonResult(quotaService.updateStatus(id, toStatus));
    }

    @RequestMapping("/delById")
    @ResponseBody
    //逻辑删除
    public Map<String, Object> delById(@RequestParam(value = "id", required = true) long id) {
        //废弃指标时，应当无规则引用
        if (ruleQuotaLinkService.existsQuota(id)) {
            return MvUtils.formatJsonResult(new ResultCode(-1, "废弃指标失败，存在规则引用该指标, id:" + id));
        }
        return MvUtils.formatJsonResult(quotaService.updateStatus(id, Constants.STATUS_DISCARD));
    }

}
