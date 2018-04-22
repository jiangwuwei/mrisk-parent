package com.zoom.risk.operating.quotameta.controller;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.quotameta.po.ParamInstance;
import com.zoom.risk.operating.quotameta.po.ParamTemplate;
import com.zoom.risk.operating.quotameta.po.QuotaDefinition;
import com.zoom.risk.operating.quotameta.service.QuotaMetaService;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

@Controller
@RequestMapping("/quotaDefinitionController")
public class QuotaDefinitionController {
	private static final Logger logger = LogManager.getLogger(QuotaDefinitionController.class);

	@Resource(name="sessionManager")
	private SessionManager sessionManager;

	@Resource(name="quotaMetaService")
	private QuotaMetaService quotaMetaService;

	@ResponseBody
	@RequestMapping("/delParamInstance")
	public Map<String,Object> delParamInstance(@RequestParam(value = "id", required = true) Long id){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			sessionManager.runWithSession(() -> quotaMetaService.delParamInstance(id), DBSelector.OPERATING_MASTER_DB);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@RequestMapping("/findParamInstance")
	public ModelAndView findParamInstance(@RequestParam(value="id", required=true) Long id){
		ModelAndView mView =MvUtils.getView("/quotameta/AddParamInstanceDiv");
		ParamInstance paramInstance = sessionManager.runWithSession(() -> quotaMetaService.findParamInstance(id), DBSelector.OPERATING_MASTER_DB);
		mView.addObject("instance", paramInstance );
		return mView;
	}

	@ResponseBody
	@RequestMapping("/saveParamInstance")
	public Map<String,Object> saveParamInstance(ParamInstance paramInstance){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			ParamInstance old = sessionManager.runWithSession(() -> quotaMetaService.findParamInstance(paramInstance.getId()), DBSelector.OPERATING_MASTER_DB);
			old.setChineseName(paramInstance.getChineseName());
			old.setName(paramInstance.getName());
			old.setDataType(paramInstance.getDataType());
			old.setDescription(paramInstance.getDescription());
			old.setDefaultValue(paramInstance.getDefaultValue());
			old.setMandatory(paramInstance.getMandatory());
			sessionManager.runWithSession(() -> quotaMetaService.updateParamInstance(old), DBSelector.OPERATING_MASTER_DB);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@RequestMapping("/findQuotaDefinition")
	public ModelAndView findQuotaDefinition(@RequestParam(value="id", required=false) Long id){
		ModelAndView mView =MvUtils.getView("/quotameta/AddDefinitionDiv");
		QuotaDefinition definition = new QuotaDefinition();
		if ( id != null ) {
			definition = sessionManager.runWithSession(() -> quotaMetaService.findDefinitionById(id), DBSelector.OPERATING_MASTER_DB);
		}
		mView.addObject("definition", definition);
		mView.addObject("sourceList", sessionManager.runWithSession(() -> quotaMetaService.findSourceDim(), DBSelector.OPERATING_MASTER_DB));
		return mView;
	}


	@RequestMapping("/selectDefinitionTemplate")
	public ModelAndView selectDefinitionTemplate(@RequestParam(value="id", required=true) Long id){
		ModelAndView mView =MvUtils.getView("/quotameta/SelectDefinitionTemplateDiv");
		List<ParamInstance>  selectList = sessionManager.runWithSession(()->quotaMetaService.findByQuotaId(id), DBSelector.OPERATING_MASTER_DB);
		List<Long> selected = new ArrayList<>();
		for (ParamInstance instance : selectList) {
			selected.add(instance.getTemplateId());
		}
		List<ParamTemplate> allList =  sessionManager.runWithSession(()->quotaMetaService.findAllParamTemplate(), DBSelector.OPERATING_MASTER_DB);
	    List<Map<String,Object>> resultList = new ArrayList<>();
		allList.forEach((template -> {
			Map<String,Object> map = new HashMap<>();
			map.put("id",template.getId());
			map.put("value",template.getChineseName()+"(" + template.getName()+ ")");
			int flag = 0;
			if ( selected.contains(template.getId())){
				flag = 1;
				//continue;
			}else {
				map.put("flag", flag);
				resultList.add(map);
			}
		}));
		mView.addObject("allList",resultList);
		return mView;
	}

	@ResponseBody
	@RequestMapping("/saveDefinitionTemplate")
	public Map<String,Object> saveDefinitionTemplate(@RequestParam(value="id", required=true) Long id, @RequestParam(value="templateId", required=true) Long[] templateId){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			sessionManager.runWithSession(() -> quotaMetaService.saveQuotaDefinitionLink(id,templateId), DBSelector.OPERATING_MASTER_DB);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@ResponseBody
	@RequestMapping("/delQuotaDefinition")
	public Map<String,Object> delQuotaDefinition(@RequestParam(value = "id", required = true) Long id){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			sessionManager.runWithSession(() -> quotaMetaService.delQuotaDefinition(id), DBSelector.OPERATING_MASTER_DB);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@ResponseBody
	@RequestMapping("/saveQuotaDefinition")
	public Map<String,Object> saveQuotaDefinition(QuotaDefinition  quotaDefinition){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			if ( quotaDefinition.getId() != null ){
				final Long tm = quotaDefinition.getId();
				QuotaDefinition old = sessionManager.runWithSession(() -> quotaMetaService.findDefinitionById(tm), DBSelector.OPERATING_MASTER_DB);
				old.setSourceId(quotaDefinition.getSourceId());
				old.setChineseName(quotaDefinition.getChineseName());
				old.setParamName(quotaDefinition.getParamName());
				old.setDataType(quotaDefinition.getDataType());
				old.setDescription(quotaDefinition.getDescription());
				sessionManager.runWithSession(() -> quotaMetaService.updateQuotaDefinition(old), DBSelector.OPERATING_MASTER_DB);
			}else {
				sessionManager.runWithSession(() -> quotaMetaService.saveQuotaDefinition(quotaDefinition), DBSelector.OPERATING_MASTER_DB);
			}
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}


}
