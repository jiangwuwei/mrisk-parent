package com.zoom.risk.operating.decision.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.decision.po.TPolicies;
import com.zoom.risk.operating.decision.po.TQuota;
import com.zoom.risk.operating.decision.proxy.DtPoliciesServiceProxy;
import com.zoom.risk.operating.decision.proxy.DtQuotaProxyService;
import com.zoom.risk.operating.decision.vo.ParamInstanceVo;
import com.zoom.risk.operating.decision.vo.QuotaTemplateVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/dtQuotaController")
public class DtQuotaController {
	private static final Logger logger = LogManager.getLogger(DtQuotaController.class);

	@Resource(name="dtQuotaProxyService")
	private DtQuotaProxyService dtQuotaProxyService;

	@Resource(name="dtPoliciesServiceProxy")
	private DtPoliciesServiceProxy dtPoliciesServiceProxy;



	@RequestMapping("/policiesQuotaConfig")
    public ModelAndView policiesQuotaConfig(){
		ModelAndView mView =MvUtils.getView("/decisiontree/PoliciesQuotaConfig");
		List<TPolicies> list = dtPoliciesServiceProxy.selectAll();
		list.forEach( tPolicies -> {
			List<TQuota> quotas = dtQuotaProxyService.findQuotas(tPolicies.getSceneNo());
			quotas.forEach( quota->{
				quota.setParamsList(JSON.parseObject(quota.getRequestParams(), new TypeToken<ArrayList<ParamInstanceVo>>(){}.getType()));
			});
			tPolicies.setQuotasList(quotas);
		});
    	mView.addObject("list", list);
    	return mView;
    }

	@RequestMapping("/selectQuota")
	public ModelAndView selectQuota(@RequestParam(value="sceneNo",required=true) String sceneNo){
		ModelAndView mView =MvUtils.getView("/decisiontree/part/SelectQuotaDiv");
		List<TQuota> list = dtQuotaProxyService.findQuotas(sceneNo);
		List<Long> templateIdList = new ArrayList<>();
		list.forEach( quota -> templateIdList.add(quota.getTemplateId()));
		List<QuotaTemplateVo> allList = dtQuotaProxyService.findQuotaTemplate();
		List<QuotaTemplateVo> resultList = new ArrayList<>();
		allList.forEach(	vo -> {
			if ( ! templateIdList.contains(vo.getId())){
				resultList.add(vo);
			}
		});
		mView.addObject("sceneNo", sceneNo);
		mView.addObject("list",resultList);
		return mView;
	}

	@RequestMapping("/findQuota")
	public ModelAndView findQuota(@RequestParam(value="quotaId",required=true) Long quotaId){
		ModelAndView mView =MvUtils.getView("/decisiontree/part/EditQuotaDiv");
		TQuota quota = dtQuotaProxyService.findQuotaById(quotaId);
		mView.addObject("quota",quota);
		return mView;
	}

	@ResponseBody
	@RequestMapping("/saveQuota")
	public Map<String,Object> saveQuota(@RequestParam(value="id", required=true) Long id, @RequestParam(value="chineseName", required=true) String chineseName,
										@RequestParam(value="description", required=true) String description ){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			dtQuotaProxyService.updateQuota(id,chineseName,description);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@ResponseBody
	@RequestMapping("/saveQuotaForPolicies")
	public Map<String,Object> saveQuotaForPolicies(@RequestParam(value="sceneNo", required=true) String sceneNo, @RequestParam(value="quotaTemplateId", required=true) Long[] quotaTemplateId){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			dtQuotaProxyService.saveQuotasForPolicies(sceneNo,quotaTemplateId);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@ResponseBody
	@RequestMapping("/delQuota")
	public Map<String, Object> delQuota(@RequestParam(value="id",required=true) Long id){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			dtQuotaProxyService.delQuota(id);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@RequestMapping("/findQuotaParam")
	public ModelAndView findQuotaParam(@RequestParam(value="quotaId",required=true) Long quotaId,
									   @RequestParam(value="paramName",required=true) String paramName){
		ModelAndView mView =MvUtils.getView("/decisiontree/part/EditQuotaParamDiv");
		TQuota quota = dtQuotaProxyService.findQuotaById(quotaId);
		List<ParamInstanceVo> paramsList = JSON.parseObject(quota.getRequestParams(), new TypeToken<ArrayList<ParamInstanceVo>>(){}.getType());
		for(ParamInstanceVo vo : paramsList ){
			if ( vo.getName().equals(paramName) ){
				mView.addObject("para", vo);
				break;
			}
		}
		return mView;
	}

	@ResponseBody
	@RequestMapping("/updateQuotaParam")
	public Map<String, Object> updateQuotaParam(@RequestParam(value="id",required=true) Long id, ParamInstanceVo vo){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			dtQuotaProxyService.updateQuotaParam(id, vo);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}


}
