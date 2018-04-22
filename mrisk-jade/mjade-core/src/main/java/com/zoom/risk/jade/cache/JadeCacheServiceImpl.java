package com.zoom.risk.jade.cache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.zoom.risk.jade.dao.JadeSceneMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoom.risk.jade.model.Scene;
import com.zoom.risk.jade.model.SceneField;


/**
 * Jade config cache
 * @author jiangyulin
 *
 */
@Service("jadeCacheService")
public class JadeCacheServiceImpl implements JadeCacheService {
	private static final Logger logger = LogManager.getLogger(JadeCacheServiceImpl.class);
	
	@Resource(name="jadeSceneMapper")
	private JadeSceneMapper jadeSceneMapper;
	
	@Resource(name="jadeCacheService")
	private JadeCacheService selfCacheService;
	
	private  Map<String,List<SceneField>> sceneConfigCache = new HashMap<String,List<SceneField>>();
	private Map<String, Scene> sceneCache=new HashMap<String, Scene>();
	private long lastupdateTime = 0;

	@PostConstruct
	public void init(){
		selfCacheService.refreshCache();
	}
	
	@Override
	@Transactional(readOnly = true)
	public void refreshCache(){
		Map<String,List<SceneField>> resultFieldsCache = new HashMap<String,List<SceneField>>();
		Map<String, Scene> resultSceneCache = new HashMap<String,Scene>();
		try{
			//only need to change max update time for scene config table, not scene
			Timestamp dbLastTime = jadeSceneMapper.findMaxModifiedTime();
			if ( dbLastTime != null && dbLastTime.getTime() > this.lastupdateTime ){
				List<Scene> sceneList = jadeSceneMapper.findScenes();
				for( Scene scene : sceneList ){
					resultSceneCache.put(scene.getSceneNo(), scene);
					resultFieldsCache.put(scene.getSceneNo(), new ArrayList<SceneField>());
				}
				logger.info("Load " + sceneList.size() + " valid scenes ");
				List<SceneField> sceneFields = jadeSceneMapper.findValidSceneFields();
				for( SceneField sceneField : sceneFields ){
					for( Scene scene : sceneList ){
						Long sceneId = scene.getId();
						if ( sceneId == sceneField.getSceneId()){
							List<SceneField> temList = resultFieldsCache.get(scene.getSceneNo());
							if ( temList != null ){
								temList.add(sceneField);
							}
						}
					}
				}
				sceneConfigCache = resultFieldsCache;
				sceneCache = resultSceneCache;
				this.lastupdateTime = dbLastTime.getTime();
				logger.info("JadeCacheService has been reloaded and last updated time has been changed to " + lastupdateTime );
			}else{
				logger.info("It does not need to load any db this time");
			}
		}catch(Exception e){
			logger.error("",e);
		}
	}
	

	@Override
	public Scene getScene(String sceneNo ){
		return sceneCache.get(sceneNo);
	}
	 

	@Override
	public List<SceneField> getSceneFields(String sceneNo ){
		return sceneConfigCache.get(sceneNo);
	}
}