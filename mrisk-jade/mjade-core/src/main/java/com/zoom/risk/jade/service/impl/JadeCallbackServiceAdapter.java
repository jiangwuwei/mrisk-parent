package com.zoom.risk.jade.service.impl;

import java.util.Map;

import com.zoom.risk.jade.exception.DynamicSqlException;
import com.zoom.risk.jade.service.JadeCallbackService;
import org.springframework.stereotype.Service;

/**
 * add personal logcs for some scene
 * @author jiangyulin
 *
 */
@Service("jadeCallbackServiceAdapter")
public class JadeCallbackServiceAdapter implements JadeCallbackService {

	@Override
	public void beforeInsert(String sceneNo, Map<String,Object> parameterMap)throws DynamicSqlException {
	}

	@Override
	public void afterInsert(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException {
	}

	@Override
	public void beforeUpdate(String sceneNo, Map<String,Object> parameterMap)throws DynamicSqlException {
	}

	@Override
	public void afterUpdate(String sceneNo, Map<String,Object> parameterMap) throws DynamicSqlException {
	}

}
