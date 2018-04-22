package com.zoom.risk.operating.scard.controller;

import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.scard.model.SCardParam;
import com.zoom.risk.operating.scard.model.SCardParamRoute;
import com.zoom.risk.operating.scard.model.SCardRule;
import com.zoom.risk.operating.scard.service.SCardParamRouteService;
import com.zoom.risk.operating.scard.service.SCardParamService;
import com.zoom.risk.operating.scard.service.SCardRuleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Controller
@RequestMapping("/scardRule")
public class SCardRuleController {
	private static final Logger logger  = LogManager.getLogger(SCardRuleController.class);
	@Autowired
	private SCardRuleService scardRuleService;


	@ResponseBody
	@RequestMapping("/saveScardRule")
	public Map<String, Object> saveScardParam(SCardRule rule){
		Long id = 0L;
		try {
			if ( rule.getId() <= 0 ){
				scardRuleService.saveScardRule(rule);
				id = rule.getId();
			}else{
				scardRuleService.updateByPrimaryKey(rule);
			}
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(ResultCode.SUCCESS,"id:"+id);
	}

	@ResponseBody
	@RequestMapping("/delScardRule")
	public Map<String, Object> delScardParamRoute(@RequestParam(value="ruleId", required=true) Long ruleId ){
		try {
			scardRuleService.deleteByPrimaryKey(ruleId);
		} catch (Exception e) {
			logger.error(e);
			return MvUtils.formatJsonResult(ResultCode.DB_ERROR);
		}
		return MvUtils.formatJsonResult(ResultCode.SUCCESS);
	}

}
