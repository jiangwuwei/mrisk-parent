package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.dao.ActionCodeDimMapper;
import com.zoom.risk.operating.ruleconfig.model.ActionCodeDim;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("actionCodeDimService")
public class ActionCodeDimService {
	
	private static final Logger logger  = LogManager.getLogger(ActionCodeDimService.class);
	
	@Autowired
	private ActionCodeDimMapper actionCodeDimMapper;
	
	public Map<String, List<ActionCodeDim>> selectAll(){
		Map<String, List<ActionCodeDim>> map = new HashMap<>();	
		try {
			List<ActionCodeDim> list = actionCodeDimMapper.selectAll();
			String temp = "";
			List<ActionCodeDim> elementList = null;
			for (ActionCodeDim actionCodeDim : list) {
				if(!actionCodeDim.getCode().equals(temp)){
					if(StringUtils.isNotBlank(temp)){
						map.put(temp, elementList);
					}
					temp = actionCodeDim.getCode();
					elementList = Lists.newArrayList();
				}
				elementList.add(actionCodeDim);
			}
			map.put(temp, elementList);
		} catch (Exception e) {
			logger.error(e);
		}
		return map;
	}
	
	public boolean update(Map<String, String> map){
		try {
			int ret = actionCodeDimMapper.updateByPrimaryKey(map);
			return (ret >0)?true:false;
		} catch (Exception e) {
			logger.error(e);
			
		}
		return false;
	}
}
