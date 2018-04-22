package com.zoom.risk.jade.cache;

import java.util.List;

import com.zoom.risk.jade.model.Scene;
import com.zoom.risk.jade.model.SceneField;

public interface JadeCacheService {

	void refreshCache();

	Scene getScene(String sceneNo);

	List<SceneField> getSceneFields(String sceneNo);

}