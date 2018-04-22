package com.zoom.risk.operating.decision.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.decision.po.TQuota;
import com.zoom.risk.operating.decision.po.TQuotaTemplate;
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
@RequestMapping("/dtQuotaTemplateController")
public class DtQuotaTemplateController {
	private static final Logger logger = LogManager.getLogger(DtQuotaTemplateController.class);

	@Resource(name="dtQuotaProxyService")
	private DtQuotaProxyService dtQuotaProxyService;

    @RequestMapping("/quotaTemplateList")
    public ModelAndView quotaTemplateList(){
		ModelAndView mView =MvUtils.getView("/decisiontree/QuotaTemplateList");
		List<QuotaTemplateVo> list = dtQuotaProxyService.findQuotaTemplate();
		list.forEach((quotaTemplate)->{
			quotaTemplate.setParamList(JSON.parseObject(quotaTemplate.getRequestParams(), new TypeToken<ArrayList<ParamInstanceVo>>(){}.getType()));
		});
    	mView.addObject("list", list);
    	return mView;
    }

	@RequestMapping("/findQuotaTemplate")
	public ModelAndView findQuotaTemplate(@RequestParam(value="id",required=false) Long id){
		ModelAndView mView =MvUtils.getView("/decisiontree/part/AddQuotaTemplate");
		TQuotaTemplate quota = new TQuotaTemplate();
		if ( id != null ) {
			quota = dtQuotaProxyService.findQuotaTemplateById(id);
		}
		//mView.addObject("sourceList", dtQuotaProxyService.findDimByCode("QuotaSourceType"));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id","9");
		map.put("name","上下文(内部)");
		mView.addObject("sourceList", Arrays.asList(map));
		mView.addObject("quota", quota);
		return mView;
	}


	@ResponseBody
    @RequestMapping("/saveQuotaTemplate")
    public Map<String, Object> saveQuotaTemplate(TQuotaTemplate template){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			if ( template.getId() == null ){
				template.setSourceType(TQuota.SOURCE_TYPE_1);
				dtQuotaProxyService.saveQuotaTemplate(template);
			}else{
				TQuotaTemplate old = dtQuotaProxyService.findQuotaTemplateById(template.getId());
				old.setSourceType(TQuota.SOURCE_TYPE_1);
				old.setChineseName(template.getChineseName());
				old.setParamName(template.getParamName());
				old.setDescription(template.getDescription());
				old.setDataType(template.getDataType());
				dtQuotaProxyService.updateQuotaTemplate(old);
			}
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
    }

    @ResponseBody
	@RequestMapping("/delQuotaTemplate")
	public Map<String, Object> delQuotaTemplate(@RequestParam(value="id",required=true) Long id){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			dtQuotaProxyService.delQuotaTemplate(id);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}
}
