package com.zoom.risk.jade.api.impl;

import com.zoom.risk.jade.api.JadeDataApi;
import com.zoom.risk.jade.cache.JadeCacheService;
import com.zoom.risk.jade.model.Scene;
import com.zoom.risk.jade.model.SceneField;
import com.zoom.risk.jade.proxy.CommonQuerySvc;
import com.zoom.risk.jade.service.JadeService;
import com.zoom.risk.jade.service.RiskEventService;
import com.zoom.risk.jade.utils.SqlUtils;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 风控网关统一服务
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年8月22日
 */
@Service("jadeDataApi")
public class JadeDataApiImpl implements JadeDataApi {
	private static final Logger logger = LogManager.getLogger(JadeDataApiImpl.class);
	
	private static final String RISK_SCENE = "scene";
	
	@Resource(name="jadeCacheService")
	private JadeCacheService jadeCacheService;
	
	@Resource(name="jadeServiceProxy")
	private JadeService jadeServiceProxy;
	
	@Resource(name="riskEventService")
	private RiskEventService riskEventService;
	
	@Resource(name="jadeCommonQueryProxy")
	private CommonQuerySvc jadeCommonQueryProxy;
	
	@Override
	public int insertEvent(Map<String, Object> parameterMap){
		LsManager.getInstance().check();
		long time = System.currentTimeMillis();
		//瑶池配置的接入场景必须有场景号
		String sceneNo = (String)parameterMap.get(RISK_SCENE);
		int resultCode = 0 ;
		if ( !StringUtils.isEmpty(sceneNo) ){
			sceneNo = sceneNo.substring(0,4);
			Scene scene = jadeCacheService.getScene(sceneNo);
			try{
				//added 2015-10-09, events are located in a single database
				riskEventService.singleInsertMap(parameterMap);
				List<SceneField> sceneConfigField = jadeCacheService.getSceneFields(sceneNo);
				resultCode = jadeServiceProxy.saveScene(sceneNo, scene.getTableName(), sceneConfigField, parameterMap);
			}catch(Throwable e){
				logger.error("Input map:" + parameterMap , e);
			}
		}
		logger.info("insertEvent takes time: {}", System.currentTimeMillis()-time);
		return resultCode;
	}

	@Override
	public int updateEvent(Map<String, Object> parameterMap) {
		long time = System.currentTimeMillis();
		//瑶池配置的接入场景必须有场景号
		String sceneNo = (String)parameterMap.get(RISK_SCENE);
		int resultCode = 0 ;
		if ( !StringUtils.isEmpty(sceneNo) ){
			sceneNo = sceneNo.substring(0,4);
			Scene scene = jadeCacheService.getScene(sceneNo);
			try{
				riskEventService.singleUpdateMap(parameterMap);
				List<SceneField> sceneConfigField = jadeCacheService.getSceneFields(sceneNo);
				resultCode = jadeServiceProxy.updateScene(sceneNo, scene.getTableName(), sceneConfigField, parameterMap);
			}catch(Throwable e){
				logger.error("Input map:" + parameterMap , e);
			}
		}
		logger.info("updateEvent takes time: {}", System.currentTimeMillis()-time);
		return resultCode;
	}


	/**
	 * 类型转换，将数字类型的字符串类型 转换成数字类型
	 * @param riskInput
	 * @return
	 */
	public Map<String, Object> convert2DatabaseType(Map<String, Object> riskInput){
		String sceneNo = String.valueOf(riskInput.get(RISK_SCENE)).substring(0,4);
		List<SceneField> sceneConfigField = jadeCacheService.getSceneFields(sceneNo);
		try{
            sceneConfigField.forEach(
                (field)-> {
                    int dbType = field.getDbType();
                    String key = field.getParamName();
                    if ( dbType == SceneField.DbType_Bigint ||  dbType == SceneField.DbType_Int  || dbType == SceneField.DbType_Decimal ){
                        Object strValue = riskInput.get(key);
                        if ( strValue != null && (!strValue.toString().equals("")) ) {
                            Object objectValue = SqlUtils.convert(dbType, strValue.toString());
                            riskInput.put(key, objectValue);
                        }
                    }
                }
            );
		}catch(Throwable e){
			logger.error("Converting the orginal riskInput happens error" , e);
		}
		return riskInput;
	}


	@Override
	public <R> R queryDataBySQL(String sql, Class<R> c){
		R r = null;
		long time = System.currentTimeMillis();
		try {
			LsManager.getInstance().check();
			if (c.isInterface()) {
				throw new RuntimeException("Class should be instantiated, not interface " + c);
			}
			if ( c.getName().startsWith("java.lang.Object")){
				r = c.newInstance();
			}else if (c.isInstance(new BigDecimal(0))) {
				r = c.getConstructor(String.class).newInstance("0");
			}else{
				r = c.newInstance();
			}
			if (r instanceof BigDecimal) {
				r = (R) jadeCommonQueryProxy.getDataDecimalByInput(sql);
			} else if (r instanceof String) {
				r = (R) jadeCommonQueryProxy.getDataStringByInput(sql);
			} else if (r instanceof Map) {
				r = (R) jadeCommonQueryProxy.getDataMapByInput(sql);
			} else if (r instanceof List) {
				r = (R) jadeCommonQueryProxy.getDataListByInput(sql);
			}else{
				r = (R) jadeCommonQueryProxy.getObjectByInput(sql);
			}
			long takingTime = System.currentTimeMillis()-time;
			int includeIp = sql.indexOf("zoom_risk_base_geo_ip");
			String slowQueryFlag =  ( takingTime > 30 && includeIp == -1 )? "slow query":"";
			logger.info("Query time: {},  {} sql : {}", takingTime, slowQueryFlag, sql);
			return r;
		} catch (Throwable e) {
			logger.error("SQL: " + sql + " happens error ", e );
			throw new RuntimeException("Executing sql happens errors", e);
		}
	}
}
