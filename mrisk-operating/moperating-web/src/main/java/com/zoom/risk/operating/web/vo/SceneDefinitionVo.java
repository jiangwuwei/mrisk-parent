package com.zoom.risk.operating.web.vo;

/**
 * Created by liyi8 on 2017/3/16.
 */
public class SceneDefinitionVo {
    private Long id;
    private String sceneNo;
    private String sceneName;

    public SceneDefinitionVo(Long id, String sceneNo, String sceneName){
        this.id = id;
        this.sceneNo = sceneNo;
        this.sceneName = sceneName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }
}
