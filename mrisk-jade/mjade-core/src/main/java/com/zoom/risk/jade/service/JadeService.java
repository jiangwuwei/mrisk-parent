package com.zoom.risk.jade.service;

import java.util.List;
import java.util.Map;

import com.zoom.risk.jade.exception.DynamicSqlException;
import com.zoom.risk.jade.model.SceneField;


public interface JadeService {

	public int saveScene(String sceneNo, String tableName, List<SceneField> fields, Map<String,Object> parameterMap) throws DynamicSqlException;

	public int updateScene(String sceneNo, String tableName, List<SceneField> fields, Map<String,Object> parameterMap) throws DynamicSqlException;

}