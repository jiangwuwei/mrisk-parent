package com.zoom.risk.operating.decision.controller;

import com.zoom.risk.operating.common.utils.MvUtils;
import com.zoom.risk.operating.decision.po.TDim;
import com.zoom.risk.operating.decision.proxy.DtDimProxyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dtDimController")
public class DtDimController {
	private static final Logger logger = LogManager.getLogger(DtDimController.class);

	@Resource(name="dtDimProxyService")
	private DtDimProxyService dtDimProxyService;

	@RequestMapping("/dimList")
    public ModelAndView dimList(@RequestParam(value="code",required=true) String code){
		ModelAndView mView =MvUtils.getView("/decisiontree/DimList");
    	mView.addObject("list", dtDimProxyService.findByCode(code));
    	return mView;
    }

	@RequestMapping("/saveDim")
	public ModelAndView saveDim(TDim dim){
		boolean flag = true;
		try{
			dtDimProxyService.insert(dim);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		return dimList(dim.getCode());
	}

	@ResponseBody
	@RequestMapping("/delDim")
	public Map<String,Object> delDim(TDim dim){
		Map<String,Object> map = new HashMap<>();
		boolean flag = true;
		try{
			dtDimProxyService.delete(dim);
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		map.put("resultCode",flag);
		return map;
	}
}
