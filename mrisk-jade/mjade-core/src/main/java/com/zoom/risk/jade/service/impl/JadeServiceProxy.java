package com.zoom.risk.jade.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zoom.risk.jade.exception.DynamicSqlException;
import com.zoom.risk.jade.model.SceneField;
import com.zoom.risk.jade.service.JadeCallbackService;
import com.zoom.risk.jade.service.JadeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * we can adding common logics for all kinds of scenes here 
 * @author jiangyulin
 *
 */
@Service("jadeServiceProxy")
public class JadeServiceProxy implements JadeService {
	
	@Resource(name="jadeService")
	private JadeService jadeService;
	
	@Resource(name="universualJadeCallbackService")
	private JadeCallbackService universualJadeCallbackService;
	
	@Override
	@Transactional
	public int saveScene(String sceneNo, String tableName, List<SceneField> fields, Map<String,Object> parameterMap) throws DynamicSqlException {
		int result = 0;
		universualJadeCallbackService.beforeInsert(sceneNo, parameterMap);
		result = jadeService.saveScene(sceneNo, tableName, fields, parameterMap);
		universualJadeCallbackService.afterInsert(sceneNo, parameterMap);
		return result;
	}

	@Override
	@Transactional
	public int updateScene(String sceneNo, String tableName, List<SceneField> fields, Map<String,Object> parameterMap) throws DynamicSqlException {
		int result = 0;
		universualJadeCallbackService.beforeUpdate(sceneNo, parameterMap);
		result = jadeService.updateScene(sceneNo, tableName, fields, parameterMap);
		universualJadeCallbackService.afterUpdate(sceneNo, parameterMap);
		return result;
	}

}
