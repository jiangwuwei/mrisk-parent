package com.zoom.risk.operating.scard.controller;

import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.ruleconfig.service.SceneConfigService;
import com.zoom.risk.operating.scard.model.SCard;
import com.zoom.risk.operating.scard.model.SCardParam;
import com.zoom.risk.operating.scard.model.SCardParamVo;
import com.zoom.risk.operating.scard.service.SCardParamService;
import com.zoom.risk.operating.scard.service.SCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller
@RequestMapping("/scardParam")
public class SCardParamController {
	private static final Logger logger  = LogManager.getLogger(SCardParamController.class);
	@Autowired
	private SCardParamService scardParamService;
	@Autowired
	private SCardService scardService;
	@Autowired
	private SceneConfigService sceneConfigService;


	@RequestMapping("/selectParamList")
	public ModelAndView selectParamList(@RequestParam(value="scardId", required=true) Long scardId){
		ModelAndView mView =MvUtils.getView("/scard/SelectParamList");
		List<SCardParamVo> list = scardParamService.getSelectParamsGroupByType(scardId);
		mView.addObject("scardParamsList", list);
		return mView;
	}

	@ResponseBody
	@RequestMapping("/saveSelectParams")
	public Map<String, Object> saveSelectParams(@RequestParam(value="paramId", required=true) Long[] paramIds,
										 @RequestParam(value="scardId", required=true) Long scardId){
		try {
			List<Map<String,Object>> list = sceneConfigService.getScardParams();
			scardParamService.saveParams(list, scardId, paramIds);
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping("/updateParams")
	public Map<String, Object> updateParams(SCardParam sCardParam){
		try {
			scardParamService.updateByPrimaryKey(sCardParam);
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping("/delScardParam")
	public Map<String, Object> delScardParam(@RequestParam(value="paramId", required=true) Long paramId ){
		try {
			 scardParamService.deleteByPrimaryKey(paramId);
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(ResultCode.SUCCESS);
	}


	@RequestMapping("/findScardParam")
	public ModelAndView findScardParam(@RequestParam(value="paramId", required=true) Long paramId){
		ModelAndView mView =MvUtils.getView("/scard/SCardRouteConfig");
		SCardParam scardParam = scardParamService.findScardParamAndRoutes(paramId);
		SCard sCard = scardService.selectByPrimaryKey(scardParam.getScardId());
		mView.addObject("scardParam", scardParam);
		mView.addObject("sCard", sCard);
		return mView;
	}
}
