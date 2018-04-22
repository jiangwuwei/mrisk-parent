package com.zoom.risk.operating.scard.controller;

import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.ruleconfig.service.SceneConfigService;
import com.zoom.risk.operating.scard.model.SCardParam;
import com.zoom.risk.operating.scard.model.SCardParamRoute;
import com.zoom.risk.operating.scard.model.SCardParamVo;
import com.zoom.risk.operating.scard.service.SCardParamRouteService;
import com.zoom.risk.operating.scard.service.SCardParamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller
@RequestMapping("/scardParamRoute")
public class SCardParamRouteController {
	private static final Logger logger  = LogManager.getLogger(SCardParamRouteController.class);
	@Autowired
	private SCardParamRouteService scardParamRouteService;
	@Autowired
	private SCardParamService scardParamService;

	@ResponseBody
	@RequestMapping("/saveScardParam")
	public Map<String, Object> saveScardParam(SCardParamRoute route){
		Long id = 0L;
		try {
			SCardParam scardParam = scardParamService.selectPrimaryKey(route.getParamId());
			route.setDbType(scardParam.getDbType());
			route.setParamName(scardParam.getParamName());
			if ( route.getId() <= 0 ){
				scardParamRouteService.saveParamRoute(route);
				id = route.getId();
			}else{
				scardParamRouteService.updateByPrimaryKey(route);
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(ResultCode.SUCCESS,"id:"+id);
	}

	@ResponseBody
	@RequestMapping("/delScardParamRoute")
	public Map<String, Object> delScardParamRoute(@RequestParam(value="routeId", required=true) Long routeId ){
		try {
			scardParamRouteService.deleteByPrimaryKey(routeId);
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(ResultCode.SUCCESS);
	}

}
