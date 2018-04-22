package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.dao.QuotaDimMapper;
import com.zoom.risk.operating.ruleconfig.model.QuotaDim;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("quotaDimService")
public class QuotaDimService {
	
	private static final Logger logger  = LogManager.getLogger(QuotaDimService.class);
	
	@Autowired
	private QuotaDimMapper quotaDimMapper;
	
	public Map<String, List<QuotaDim>> selectAll(){
		Map<String, List<QuotaDim>> map = new HashMap<>();	
		try {
			List<QuotaDim> list = quotaDimMapper.selectAll();
			String temp = "";
			List<QuotaDim> elementList = null;
			for (QuotaDim quotaDim : list) {
				if(!quotaDim.getCode().equals(temp)){
					if(StringUtils.isNotBlank(temp)){
						map.put(temp, elementList);
					}
					temp = quotaDim.getCode();
					elementList = Lists.newArrayList();
				}
				elementList.add(quotaDim);
			}
			map.put(temp, elementList);
		} catch (Exception e) {
			logger.error(e);
		}
		return map;
	}
}
