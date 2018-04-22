package com.zoom.risk.operating.ruleconfig.controller;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.model.Policies;
import com.zoom.risk.operating.ruleconfig.model.Policy;
import com.zoom.risk.operating.ruleconfig.model.Scenes;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/policies")
public class PoliciesController {
	
	@Autowired
	private PoliciesService policiesService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private PolicyRouterService policyRouterService;
	@Autowired
	private ScenesService scenesService;
	@Autowired
	private QuotaService quotaService;

	private static final Logger logger  = LogManager.getLogger(PoliciesController.class);
	
	@RequestMapping("/list")
    public ModelAndView list(@RequestParam(value="sceneNo",required=false,defaultValue="all") String sceneNo, 
    		@RequestParam(value="policiesName",required=false,defaultValue="") String policiesName, 
    		@RequestParam(value="offset", required=false,defaultValue="0") int offset,
    		@RequestParam(value="limit",required=false,defaultValue="0") int limit){
		ModelAndView mView =MvUtils.getView("/ruleconfig/Policies");
		LsManager.getInstance().check();
		List<Policies> policiesList = null;
		if(StringUtils.isNotBlank(policiesName)){//根据名称搜索
			policiesList = policiesService.selectByName(policiesName, offset, limit);
		}else{
			policiesList = policiesService.selectPage(sceneNo, offset, limit);
		}
		List<String> sceneNoList = policiesService.getSceneNoList(policiesList, sceneNo);
		List<Policy> policyList = Lists.newArrayList();
		if(!sceneNoList.isEmpty()){
			policyList = policyService.selectBySceneNoList(sceneNoList);
		}
		List<String> policyRouterSceneNo = policyRouterService.selectDistinctSceneNo();
		mView.addObject("limit", limit);
		mView.addObject("offset", offset);
		mView.addObject("sceneNo", sceneNo);
		mView.addObject("policiesName", policiesName);
		mView.addObject("allScenes", scenesService.selectByType(Scenes.SCENE_TYPE_ANTIFRAUD));
		mView.addObject("list", policiesService.getPoliciesPairList(policiesList, policyList, policyRouterSceneNo));
		return mView;
    }
    
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(Policies policies){
    	try {
			if(policiesService.exists(policies.getSceneNo(), policies.getName())){
				return MvUtils.formatJsonResult(new ResultCode(-1, "已经存在此场景名称或者标识"));
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
    	return MvUtils.formatJsonResult(policiesService.insert(policies));
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(Policies policies){
    	return MvUtils.formatJsonResult(policiesService.update(policies));
    }
    
    @RequestMapping("/delById")
    @ResponseBody
    public Map<String, Object> delById(@RequestParam(value="sceneNo",required=true) String sceneNo){
    	try {
			if(policyService.selectCountBySceneNo(sceneNo) > 0){
				return MvUtils.formatJsonResult(new ResultCode(-1, "该策略集已有对应的策略，不能删除"));
			}
            if(quotaService.selectCount(sceneNo) > 0){
				return MvUtils.formatJsonResult(new ResultCode(-2, "该策略集已有对应的指标，不能删除"));
			}

		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
    	return MvUtils.formatJsonResult(policiesService.delById(sceneNo));
    }
}
