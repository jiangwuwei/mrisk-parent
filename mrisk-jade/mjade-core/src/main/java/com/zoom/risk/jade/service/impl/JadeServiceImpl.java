package com.zoom.risk.jade.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zoom.risk.jade.service.JadeCallbackService;
import com.zoom.risk.jade.service.JadeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.zoom.risk.jade.exception.DynamicSqlException;
import com.zoom.risk.jade.model.SceneField;
import com.zoom.risk.jade.model.SqlModel;
import com.zoom.risk.jade.service.JadeServiceHelper;

/**
 * config this service in spring-jade.xml because of extra callback services supports
 * @author jiangyulin
 *
 */
@Service("jadeService")
public class JadeServiceImpl implements JadeService {
	private static final Logger logger = LogManager.getLogger(JadeServiceImpl.class);
	
	@Resource(name="jadeServiceHelper")
	private JadeServiceHelper jadeServiceHelper;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	private Map<String,JadeCallbackService> callbackServices;
	
	@Override
	public int saveScene(String sceneNo, String tableName, List<SceneField> fields, Map<String,Object> parameterMap)throws DynamicSqlException{
		if ( callbackServices != null ){
			JadeCallbackService service = callbackServices.get(sceneNo);
			if ( service != null ){
				service.beforeInsert(sceneNo, parameterMap);
			}
		}
		SqlModel sqlMode = jadeServiceHelper.buildInsert(tableName, fields, parameterMap);
		//logger.debug(sqlMode.toString());
		int result = jdbcTemplate.update(sqlMode.getSql(), sqlMode.getValues());
		if ( callbackServices != null ){
			JadeCallbackService service = callbackServices.get(sceneNo);
			if ( service != null ){
				service.afterInsert(sceneNo, parameterMap);
			}
		}
		return result;
	}

	
	@Override
	public int updateScene(String sceneNo, String tableName, List<SceneField> fields, Map<String,Object> parameterMap)throws DynamicSqlException{
		if ( callbackServices != null ){
			JadeCallbackService service = callbackServices.get(sceneNo);
			if ( service != null ){
				service.beforeUpdate(sceneNo, parameterMap);
			}
		}
		SqlModel sqlMode = jadeServiceHelper.buildUpdate(tableName, fields, parameterMap);
		//logger.debug(sqlMode.toString());
		int result = jdbcTemplate.update(sqlMode.getSql(), sqlMode.getValues());
		if ( callbackServices != null ){
			JadeCallbackService service = callbackServices.get(sceneNo);
			if ( service != null ){
				service.afterUpdate(sceneNo, parameterMap);
			}
		}
		return result;
	}

	public void setCallbackServices(Map<String, JadeCallbackService> callbackServices) {
		this.callbackServices = callbackServices;
	}
	
}
