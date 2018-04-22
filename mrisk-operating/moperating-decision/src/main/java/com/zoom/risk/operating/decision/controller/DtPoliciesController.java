package com.zoom.risk.operating.decision.controller;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.decision.po.TPolicies;
import com.zoom.risk.operating.decision.po.TPolicy;
import com.zoom.risk.operating.decision.po.TRule;
import com.zoom.risk.operating.decision.proxy.DtPoliciesServiceProxy;
import com.zoom.risk.operating.decision.proxy.DtPolicyServiceProxy;
import com.zoom.risk.operating.decision.proxy.RiskDecisionTreeProxyService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dtPolicies")
public class DtPoliciesController {
	
	@Autowired
	private DtPoliciesServiceProxy dtPoliciesServiceProxy;
	@Autowired
	private DtPolicyServiceProxy dtPolicyServiceProxy;
	@Autowired
	private RiskDecisionTreeProxyService riskDecisionTreeServiceProxy;

	private static final Logger logger  = LogManager.getLogger(DtPoliciesController.class);

	@RequestMapping("/validadteTree")
	public ModelAndView validadteTree(@RequestParam(value="policyId",required=true) Long policyId){
		ModelAndView mView =MvUtils.getView("/decisiontree/part/ValidateTreeDiv");
		List<TRule> mockRule = new ArrayList<>();
		int passFlag  = 1;
		try {
			mockRule = riskDecisionTreeServiceProxy.mockDecisionTree2Rule(policyId);
		}catch (Exception e){
			passFlag = 0;
			mView.addObject("errorMessage", e.getMessage());
			logger.error("", e);
		}
		mView.addObject("passFlag", passFlag);
		mView.addObject("mockRules", mockRule);
		return mView;
	}

	@RequestMapping("/list")
    public ModelAndView list(@RequestParam(value="sceneNo",required=false,defaultValue="all") String sceneNo, 
    		@RequestParam(value="policiesName",required=false,defaultValue="") String policiesName, 
    		@RequestParam(value="offset", required=false,defaultValue="0") int offset,
    		@RequestParam(value="limit",required=false,defaultValue="0") int limit){
		ModelAndView mView =MvUtils.getView("/decisiontree/DtPolicies");
		List<TPolicies> policiesList = null;
		LsManager.getInstance().check();
		if(StringUtils.isNotBlank(policiesName)){//根据名称搜索
			policiesList = dtPoliciesServiceProxy.selectByName(policiesName, offset, limit);
		}else{
			policiesList = dtPoliciesServiceProxy.selectPage(sceneNo, offset, limit);
		}
		List<String> sceneNoList = dtPoliciesServiceProxy.getSceneNoList(policiesList, sceneNo);
		List<TPolicy> policyList = Lists.newArrayList();
		if(!sceneNoList.isEmpty()){
			policyList = dtPolicyServiceProxy.selectBySceneNoList(sceneNoList);
		}

		mView.addObject("limit", limit);
		mView.addObject("offset", offset);
		mView.addObject("sceneNo", sceneNo);
		mView.addObject("policiesName", policiesName);
		mView.addObject("allScenes", riskDecisionTreeServiceProxy.selectDtScenes());
		mView.addObject("list", dtPoliciesServiceProxy.getPoliciesPairList(policiesList, policyList));
		return mView;
    }
    
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(TPolicies tPolicies){
    	try {
			if(dtPoliciesServiceProxy.exists(tPolicies.getSceneNo(), tPolicies.getName())){
				return MvUtils.formatJsonResult(new ResultCode(-1, "已经存在此场景名称或者标识"));
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
    	return MvUtils.formatJsonResult(dtPoliciesServiceProxy.insert(tPolicies));
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(TPolicies tPolicies){
    	return MvUtils.formatJsonResult(dtPoliciesServiceProxy.update(tPolicies));
    }
    
    @RequestMapping("/delById")
    @ResponseBody
    public Map<String, Object> delById(@RequestParam(value="sceneNo",required=true) String sceneNo){
		try {
			return MvUtils.formatJsonResult(dtPoliciesServiceProxy.delById(sceneNo));
		} catch (Exception e) {
			logger.error(e);
		}
		return MvUtils.formatJsonResult(false);
	}
}
