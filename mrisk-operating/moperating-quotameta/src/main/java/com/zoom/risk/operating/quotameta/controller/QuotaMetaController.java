package com.zoom.risk.operating.quotameta.controller;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.decision.po.TQuotaTemplate;
import com.zoom.risk.operating.decision.proxy.DtQuotaProxyService;
import com.zoom.risk.operating.quotameta.po.ParamTemplate;
import com.zoom.risk.operating.quotameta.service.QuotaMetaService;
import com.zoom.risk.operating.quotameta.vo.QuotaDefinitionVo;
import com.zoom.risk.platform.common.util.Utils;
import com.zoom.risk.platform.ctr.util.LsManager;
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
@RequestMapping("/quotaMetaController")
public class QuotaMetaController {
	private static final Logger logger = LogManager.getLogger(QuotaMetaController.class);

	@Resource(name="sessionManager")
	private SessionManager sessionManager;

	@Resource(name="quotaMetaService")
	private QuotaMetaService quotaMetaService;


	@Resource(name="dtQuotaProxyService")
	private DtQuotaProxyService dtQuotaProxyService;

    @RequestMapping("/paramTemplateList")
    public ModelAndView paramTemplateList(){
    	ModelAndView mView =MvUtils.getView("/quotameta/ParamTemplateList");
    	mView.addObject("list", sessionManager.runWithSession(()->quotaMetaService.findAllParamTemplate(), DBSelector.OPERATING_MASTER_DB));
    	return mView;
    }

	@RequestMapping("/quotaDefinitionList")
	public ModelAndView quotaDefinitionList(){
		LsManager.getInstance().check();
		ModelAndView mView =MvUtils.getView("/quotameta/QuotaDefinitionList");
		List<QuotaDefinitionVo> list = sessionManager.runWithSession(()->quotaMetaService.findAllQuotaDefinition(), DBSelector.OPERATING_MASTER_DB);
		for (QuotaDefinitionVo vo : list) {
			vo.setParamsList(sessionManager.runWithSession(()->quotaMetaService.findByQuotaId(vo.getId()), DBSelector.OPERATING_MASTER_DB));
			//vo.setParamsList(JSON.parseObject(vo.getRequestParams(), new TypeToken<ArrayList<ParamInstanceVo>>(){}.getType()));
		}
		mView.addObject("list", list );
		return mView;
	}

	@RequestMapping("/findParamTemplate")
	public ModelAndView findParamTemplate(@RequestParam(value="id", required=false) Long id){
		ModelAndView mView =MvUtils.getView("/quotameta/AddParamTemplateDiv");
		ParamTemplate paramTemplate = new ParamTemplate();
		if ( id != null ) {
			paramTemplate = sessionManager.runWithSession(() -> quotaMetaService.findParamTemplate(id), DBSelector.OPERATING_MASTER_DB);
		}
		mView.addObject("template", paramTemplate );
		return mView;
	}

	@ResponseBody
	@RequestMapping("/delParamTemplate")
	public Map<String,Object> delParamTemplate(@RequestParam(value = "id", required = true) Long id){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			sessionManager.runWithSession(() -> quotaMetaService.delParamTemplate(id), DBSelector.OPERATING_MASTER_DB);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}

	@ResponseBody
	@RequestMapping("/saveParamTemplate")
	public Map<String,Object> saveParamTemplate(ParamTemplate template){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			if (Utils.isEmpty(template.getDefaultValue())) {
				template.setDefaultValue(null);
			}
			if ( template.getId() != null ){
				//修改流程,只让修改3个值
				final Long tm = template.getId();
				ParamTemplate old = sessionManager.runWithSession(() -> quotaMetaService.findParamTemplate(tm), DBSelector.OPERATING_MASTER_DB);
				old.setMandatory(template.getMandatory());
				old.setDefaultValue(template.getDefaultValue());
				old.setChineseName(template.getChineseName());
				old.setDataType(template.getDataType());
				old.setName(template.getName());
				sessionManager.runWithSession(() -> quotaMetaService.updateParamTemplate(old), DBSelector.OPERATING_MASTER_DB);
			}else {
				final ParamTemplate tm = template;
				sessionManager.runWithSession(() -> quotaMetaService.saveParamTemplate(tm), DBSelector.OPERATING_MASTER_DB);
			}
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}


	@RequestMapping("/showAddedQuotaMeta")
	public ModelAndView showAddedQuotaMeta(){
		ModelAndView mView =MvUtils.getView("/quotameta/AddedQuotaMetaList");
		List<QuotaDefinitionVo> list = sessionManager.runWithSession(()->quotaMetaService.findAllQuotaDefinition(), DBSelector.OPERATING_MASTER_DB);
		List<QuotaDefinitionVo> resultList = new ArrayList<>();
		list.forEach(vo->{
			if ( vo.getSycStatus() == 0 ) {
				resultList.add(vo);
			}
		});
		for (QuotaDefinitionVo vo : resultList) {
			vo.setParamsList(sessionManager.runWithSession(()->quotaMetaService.findByQuotaId(vo.getId()), DBSelector.OPERATING_MASTER_DB));
		}
		mView.addObject("list", resultList );
		return mView;
	}

	@ResponseBody
	@RequestMapping("/sycQuotaTemplate")
	public Map<String,Object> sycQuotaTemplate(){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		List<QuotaDefinitionVo> list = sessionManager.runWithSession(()->quotaMetaService.findAllQuotaDefinition(), DBSelector.OPERATING_MASTER_DB);
		List<TQuotaTemplate> resultList = new ArrayList<>();
		List<Long> sycList = new ArrayList<>();
		list.forEach(vo->{
			if ( vo.getSycStatus() == 0 ) {
				sycList.add(vo.getId());
				TQuotaTemplate template = new TQuotaTemplate();
				template.setSourceId(vo.getSourceId());
				template.setChineseName(vo.getChineseName());
				template.setParamName(vo.getParamName());
				template.setDataType(vo.getDataType());
				template.setSourceType(2);
				template.setDescription(vo.getDescription());
				template.setRequestParams(vo.getRequestParams());
				resultList.add(template);
			}
		});
		if ( !sycList.isEmpty() ) {
			try {
				dtQuotaProxyService.batchSaveQuotaTemplate(resultList);
				sessionManager.runWithSession(() -> quotaMetaService.sycStatusQuotaDefinition(sycList.toArray(new Long[1])), DBSelector.OPERATING_MASTER_DB);
			} catch (Exception e) {
				flag = false;
				logger.error(e);
			}
		}
		map.put("resultCode",flag);
		return map;
	}
}
