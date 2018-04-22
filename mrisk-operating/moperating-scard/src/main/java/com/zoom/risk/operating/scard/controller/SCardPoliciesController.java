package com.zoom.risk.operating.scard.controller;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.ruleconfig.model.Scenes;
import com.zoom.risk.operating.ruleconfig.service.ScenesService;
import com.zoom.risk.operating.scard.model.SCard;
import com.zoom.risk.operating.scard.model.SCardPolicies;
import com.zoom.risk.operating.scard.service.SCardService;
import com.zoom.risk.operating.scard.service.SCardPoliciesService;
import com.zoom.risk.operating.scard.service.SCardRouterService;
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
/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller
@RequestMapping("/scardPolicies")
public class SCardPoliciesController {
	private static final Logger logger  = LogManager.getLogger(SCardPoliciesController.class);

	@Autowired
	private SCardPoliciesService scardPoliciesService;
	@Autowired
	private SCardService scardDefinitionService;
	@Autowired
	private ScenesService scenesService;
	@Autowired
	private SCardRouterService scardRouterService;
	
	@RequestMapping("/list")
    public ModelAndView list(@RequestParam(value="sceneNo",required=false,defaultValue="all") String sceneNo, 
    		@RequestParam(value="policiesName",required=false,defaultValue="") String policiesName, 
    		@RequestParam(value="offset", required=false,defaultValue="0") int offset,
    		@RequestParam(value="limit",required=false,defaultValue="0") int limit){
		LsManager.getInstance().check();
		ModelAndView mView =MvUtils.getView("/scard/SCardPolicies");
		List<SCardPolicies> policiesList = null;
		if(StringUtils.isNotBlank(policiesName)){//根据名称搜索
			policiesList = scardPoliciesService.selectByName(policiesName, offset, limit);
		}else{
			policiesList = scardPoliciesService.selectPage(sceneNo, offset, limit);
		}
		List<String> sceneNoList = scardPoliciesService.getSceneNoList(policiesList, sceneNo);
		List<SCard> policyList = Lists.newArrayList();
		if(!sceneNoList.isEmpty()){
			policyList = scardDefinitionService.selectBySceneNoList(sceneNoList);
		}
		List<String> policyRouterSceneNo = scardRouterService.selectDistinctSceneNo();
		mView.addObject("limit", limit);
		mView.addObject("offset", offset);
		mView.addObject("sceneNo", sceneNo);
		mView.addObject("policiesName", policiesName);
		mView.addObject("allScenes", scenesService.selectByType(Scenes.SCENE_TYPE_SCORE_CARD));
		mView.addObject("list", scardPoliciesService.getPoliciesPairList(policiesList, policyList, policyRouterSceneNo));
		return mView;
    }
    
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(SCardPolicies policies){
    	try {
			if(scardPoliciesService.exists(policies.getSceneNo(), policies.getName())){
				return MvUtils.formatJsonResult(new ResultCode(-1, "已经存在此场景名称或者标识"));
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
    	return MvUtils.formatJsonResult(scardPoliciesService.insert(policies));
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(SCardPolicies policies){
    	return MvUtils.formatJsonResult(scardPoliciesService.update(policies));
    }
    
    @RequestMapping("/delById")
    @ResponseBody
    public Map<String, Object> delById(@RequestParam(value="sceneNo",required=true) String sceneNo){
    	try {
			if(scardDefinitionService.selectCountBySceneNo(sceneNo) > 0){
				return MvUtils.formatJsonResult(new ResultCode(-1, "该策略集已有对应的评分卡，不能删除"));
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
    	return MvUtils.formatJsonResult(scardPoliciesService.delById(sceneNo));
    }

}
