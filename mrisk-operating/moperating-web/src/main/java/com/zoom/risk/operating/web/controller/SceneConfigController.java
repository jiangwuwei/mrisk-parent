package com.zoom.risk.operating.web.controller;

import com.zoom.risk.operating.web.service.SceneDefinitionService;
import com.zoom.risk.operating.web.vo.SceneDefinitionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liyi8 on 2017/3/16.
 */
@RestController
@RequestMapping("/sceneConfig")
public class SceneConfigController {

    @Autowired
    private SceneDefinitionService sceneDefinitionService;

    @RequestMapping("/sceneDefinitionVoList")
    public List<SceneDefinitionVo> sceneDefinitionVoList(){
        return sceneDefinitionService.getSceneDefinitionVoList("");
    }
}
