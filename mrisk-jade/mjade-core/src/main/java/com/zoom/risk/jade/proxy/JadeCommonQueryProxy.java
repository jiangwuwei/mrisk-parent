package com.zoom.risk.jade.proxy;

import com.zoom.risk.jade.cache.JadeCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 查询服务有两级路由 
 * 1. 瑶池库,采用根据表名称的 pattern,这部分路由由 JadeCommonQueryProxy实现
 * 2. wyrisk以及拆分库路由,这部分路由由 LexusisRoutingDataSource 实现
 * @author jiangyulin
 *
 */
@Service("jadeCommonQueryProxy")
public class JadeCommonQueryProxy implements CommonQuerySvc{
	private static final String TABLE_PREFIX = "zoom_risk_scene_";
	private static final Pattern JADE_PATTERN = Pattern.compile("(\\s+)"+TABLE_PREFIX+"\\d{4}(_.+)?(\\s+)",Pattern.CASE_INSENSITIVE);
	
	@Resource(name="commonQuerySvc")
	private CommonQuerySvc commonQuerySvc;
	
	
	@Resource(name="jadeCacheService")
	private JadeCacheService jadeCacheService;
	
	public String getDataStringByInput(final String sql){
		return commonQuerySvc.getDataStringByInput(sql);
	}
	
	public Object getObjectByInput(final String sql){
		return commonQuerySvc.getObjectByInput(sql);
	}
	
	public BigDecimal getDataDecimalByInput(final String sql){
		return commonQuerySvc.getDataDecimalByInput(sql);
	}
	
	public Map<String,Object> getDataMapByInput(final String sql){
		return commonQuerySvc.getDataMapByInput(sql);
	}
	
	public List<Map<String,Object>> getDataListByInput(final String sql){
		return commonQuerySvc.getDataListByInput(sql);
	}
}
