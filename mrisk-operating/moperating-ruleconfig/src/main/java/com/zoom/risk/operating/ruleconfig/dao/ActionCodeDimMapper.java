package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.ActionCodeDim;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="actionCodeDimMapper")
public interface ActionCodeDimMapper {
	 List<ActionCodeDim> selectAll();
	 
	 int updateByPrimaryKey(Map<String, String> map);
}