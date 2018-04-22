package com.zoom.risk.jade.service;

import java.util.Map;

import com.zoom.risk.jade.exception.DynamicSqlException;

/**
 * 提供扩展功能
 * @author jiangyulin
 *
 */
public interface JadeCallbackService {
 
	public void beforeInsert(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException;
 
	public void afterInsert(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException;
 
	public void beforeUpdate(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException;
 
	public void afterUpdate(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException;
}
