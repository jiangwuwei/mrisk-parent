/**
 * 
 */
package com.zoom.risk.platform.datacenter.service.impl;

import com.zoom.risk.jade.api.JadeDataApi;
import com.zoom.risk.platform.datacenter.service.QuotaJadeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author jiangyulin
 *Nov 26, 2015
 */
@Service("quotaJadeService")
public class QuotaJadeServiceImpl implements QuotaJadeService {
	private static final Logger logger = LogManager.getLogger(QuotaJadeServiceImpl.class);

	@Resource(name="jadeDataApi")
	private JadeDataApi jadeDataApi;
	
	@Override
	public Object getObjectByInput(String sql) {
		return jadeDataApi.queryDataBySQL(sql, Object.class);
	}

	@Override
	public String getStringByInput(String sql) {
		return jadeDataApi.queryDataBySQL(sql, String.class);
	}

	@Override
	public BigDecimal getDecimalByInput(String sql) {
		return jadeDataApi.queryDataBySQL(sql, BigDecimal.class);
	}

	@Override
	public Map<String, Object> getMapByInput(String sql) {
		return jadeDataApi.queryDataBySQL(sql, HashMap.class);
	}

	/**
	 * There is limitation for list, only one key and one value
	 * @param sql
	 * @return
	 */
	@Override
	public List<Object> getListByInput(String sql) {
		List<Map<String, Object>> list = jadeDataApi.queryDataBySQL(sql, ArrayList.class);
		List<Object> resultList = new ArrayList();
		logger.info("list.size() = {}, and list = {} ", list.size(), list);
		if ( !list.isEmpty() ) {
			list.forEach((map) -> {
				if ( map != null ) {
					Collection<Object> collection = map.values();
					if (collection != null) {
						resultList.addAll(collection);
					}
				}
			});
		}
		return resultList;
	}
}
