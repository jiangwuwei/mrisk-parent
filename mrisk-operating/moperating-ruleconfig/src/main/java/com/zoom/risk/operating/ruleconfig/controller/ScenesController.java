package com.zoom.risk.operating.ruleconfig.controller;

import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.model.Scenes;
import com.zoom.risk.operating.ruleconfig.service.PoliciesService;
import com.zoom.risk.operating.ruleconfig.service.ScenesService;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/scenes")
public class ScenesController {
	
	private static final Logger logger  = LogManager.getLogger(ScenesController.class);
	
	@Autowired
	private ScenesService scenesService;
	@Autowired
	private PoliciesService policiesService;
	
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam String sceneNo, @RequestParam(name="offset", required=false,defaultValue="0") int offset,
    		@RequestParam(name="limit",required=false,defaultValue="20") int limit,
    		@RequestParam(name="sceneType",required=false,defaultValue="1") int sceneType){
        ModelAndView mView = MvUtils.getView("/ruleconfig/Scenes");
		mView.addObject("limit", limit);
		mView.addObject("offset", offset);
		int count = scenesService.selectCount(sceneNo, sceneType);
		mView.addObject("totalCount", count);
		mView.addObject("totalPages", (count%limit==0)?(count/limit):(count/limit+1));
		mView.addObject("sceneNo", sceneNo);
		mView.addObject("sceneType", sceneType);
		mView.addObject("allScenes", scenesService.selectByType(sceneType));
		mView.addObject("list", scenesService.selectPage(sceneNo, offset, limit,sceneType));
		return mView;
    }
    
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(Scenes scenes){
    	try {
			if(scenesService.existScene(scenes.getSceneNo(), scenes.getName())){
				return MvUtils.formatJsonResult(new ResultCode(-1, "已经存在此场景名称或者标识"));
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
    	return MvUtils.formatJsonResult(scenesService.insert(scenes));
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(Scenes scenes){
    	return MvUtils.formatJsonResult(scenesService.update(scenes));
    }
    
    @RequestMapping("/delById")
	@ResponseBody
    public Map<String, Object> delById(@RequestParam(value="sceneNo",required=true) String sceneNo){
    	if(policiesService.selectCount(sceneNo) > 0){
    		return MvUtils.formatJsonResult(new ResultCode(-1, "该场景已有对应的策略集，不能删除"));
    	}
    	return MvUtils.formatJsonResult(scenesService.delById(sceneNo));
    }
}
