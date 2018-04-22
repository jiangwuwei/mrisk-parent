/**
 * 
 */
package com.zoom.risk.platform.datacenter.proxy.impl;

import com.zoom.risk.platform.datacenter.proxy.QuotaDatabaseProxyService;
import com.zoom.risk.platform.datacenter.service.QuotaDatabaseInnerService;
import com.zoom.risk.platform.sharding.dbsharding.ServiceExecutor;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * @author jiangyulin
 *Nov 26, 2015
 */
@Service("quotaDatabaseProxyService")
public class QuotaDatabaseProxyServiceImpl implements QuotaDatabaseProxyService {
	
	@Resource(name="quotaDatabaseInnerService")
	private QuotaDatabaseInnerService quotaDatabaseInnerService;
	
	@Resource(name="sessionManager")
	private SessionManager sessionManager;

	@Override
	public Object getObjectByInput(String sql, String dsKey) {
		return	sessionManager.runWithSession(new ServiceExecutor<Object>(){
					public Object execute() {
						return quotaDatabaseInnerService.getObjectByInput(sql);
					}
				}, dsKey);
	}

	@Override
	public String getStringByInput(String sql,String dsKey){
		return  sessionManager.runWithSession(new ServiceExecutor<String>() {
					public String execute() {
						return quotaDatabaseInnerService.getStringByInput(sql);
					}
				}, dsKey);
	}

	@Override
	public BigDecimal getDecimalByInput(String sql,String dsKey){
		return	sessionManager.runWithSession(new ServiceExecutor<BigDecimal>(){
					public BigDecimal execute() {
						return quotaDatabaseInnerService.getDecimalByInput(sql);
					}
				}, dsKey);
	}

	@Override
	public Map<String, Object> getMapByInput(String sql,String dsKey){
		return	sessionManager.runWithSession(new ServiceExecutor<Map<String, Object>>(){
					public Map<String, Object> execute() {
						return quotaDatabaseInnerService.getMapByInput(sql);
					}
				}, dsKey);
	}

	@Override
	public List<Object> getListByInput(String sql,String dsKey){
		List<Map<String, Object>> list =	sessionManager.runWithSession(new ServiceExecutor<List<Map<String, Object>>>(){
												public List<Map<String, Object>> execute() {
													return quotaDatabaseInnerService.getListByInput(sql);
												}
											}, dsKey);
        List<Object> resultList = new ArrayList();
		list.forEach((map) -> {
			if ( map != null ) {
				Collection<Object> collection = map.values();
				if (collection != null) {
					resultList.addAll(collection);
				}
			}
		});
		return resultList;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
}
