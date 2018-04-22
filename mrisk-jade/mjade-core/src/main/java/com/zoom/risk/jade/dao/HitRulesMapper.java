package com.zoom.risk.jade.dao;

import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author jiangyulin
 *
 */
@ZoomiBatisRepository(value="hitRulesMapper")
public interface HitRulesMapper {
	public void batchInsertMap(List<Map<String,Object>> list);
}