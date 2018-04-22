package com.zoom.risk.jade.service;

import java.util.List;
import java.util.Map;

import com.zoom.risk.jade.exception.DynamicSqlException;
import com.zoom.risk.jade.model.SceneField;
import com.zoom.risk.jade.model.SqlModel;


public interface JadeServiceHelper {
	
	/**
	 * generate create db script
	 * @param gateWayCode
	 * @param tableComment
	 * @param fields
	 * @return
	 */
	public String generateDbScript(String sceneNo, String tableComment, List<SceneField> fields);

	/**
	 * there will be limitation, only for like Map<String,String>
	 * @param tableName
	 * @param fields
	 * @param parameterMap
	 * @return
	 */
	public SqlModel buildInsert(String tableName, List<SceneField> fields, Map<String,Object> parameterMap)throws DynamicSqlException;

	/**
	 * @param tableName
	 * @param fields
	 * @param parameterMap
	 * @return
	 * @throws LackUpdateValueException
	 */
	public SqlModel buildUpdate(String tableName, List<SceneField> fields, Map<String, Object> parameterMap)throws DynamicSqlException;

}