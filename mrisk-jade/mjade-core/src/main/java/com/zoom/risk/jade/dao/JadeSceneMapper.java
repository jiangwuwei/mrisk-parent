package com.zoom.risk.jade.dao;

import java.sql.Timestamp;
import java.util.List;

import com.zoom.risk.jade.model.Scene;
import com.zoom.risk.jade.model.SceneField;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

/**
 * 
 * @author jiangyulin
 *
 */
@ZoomiBatisRepository(value="jadeSceneMapper")
public interface JadeSceneMapper {
	
	public List<Scene> findScenes();
	
	public List<SceneField> findValidSceneFields();
	
	public Timestamp findMaxModifiedTime();
}
