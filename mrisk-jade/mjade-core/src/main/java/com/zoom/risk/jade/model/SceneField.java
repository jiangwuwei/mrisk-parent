package com.zoom.risk.jade.model;
/**
 * 场景配置的元数据
 * @author jiangyulin
 *
 */
public class SceneField extends DimField{
	//关联的字典字段
	private long fieldId;
	//场景id
	private long sceneId;
	
	public long getFieldId() {
		return fieldId;
	}
	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}
	public long getSceneId() {
		return sceneId;
	}
	public void setSceneId(long sceneId) {
		this.sceneId = sceneId;
	}
	
}
