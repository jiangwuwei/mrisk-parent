package com.zoom.risk.operating.ruleconfig.controller;

import com.google.common.collect.Maps;
import com.zoom.risk.operating.ruleconfig.service.ActionCodeDimService;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

//@Controller
//@RequestMapping("/backend")
public class BackEndController {
	@Autowired
	private ActionCodeDimService actionCodeDimService;

	@RequestMapping("/updateAcDimTable")
	public Map<String, Object> updateActionCodeDim(@RequestParam(value="conditionCode",required=true) String conditionCode, 
    		@RequestParam(value="conditionId",required=false) String conditionId, 
    		@RequestParam(value="code",required=false) String code, 
    		@RequestParam(value="id",required=false) String id, 
    		@RequestParam(value="name",required=false) String name){
		Map<String, String> map = Maps.newHashMap();
		map.put("conditionCode", conditionCode);
		if(StringUtils.isNotBlank(conditionId)){
			map.put("conditionId", conditionId);
		}
		if(StringUtils.isNotBlank(code)){
			map.put("code", code);
		}
		if(StringUtils.isNotBlank(id)){
			map.put("id", id);
		}
		if(StringUtils.isNotBlank(name)){
			map.put("name", name);
		}
		return  MvUtils.formatJsonResult(actionCodeDimService.update(map));
	}
}
