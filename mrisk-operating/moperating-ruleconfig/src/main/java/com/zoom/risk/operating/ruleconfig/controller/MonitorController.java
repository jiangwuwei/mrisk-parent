package com.zoom.risk.operating.ruleconfig.controller;

import com.zoom.risk.operating.es.vo.EventInputModel;
import com.zoom.risk.operating.ruleconfig.common.DateUtil;
import com.zoom.risk.operating.ruleconfig.model.Scenes;
import com.zoom.risk.operating.ruleconfig.service.MonitorService;
import com.zoom.risk.operating.ruleconfig.service.ScenesService;
import com.zoom.risk.operating.ruleconfig.utils.MvUtils;
import com.zoom.risk.platform.ctr.util.LsManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
	
	@Autowired
	private MonitorService monitorService;
	@Autowired
	private ScenesService scenesService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/eventList")
	public ModelAndView eventList(EventInputModel eventInputModel, @RequestParam(name = "deepSearch", required = false, defaultValue = "0") int deepSearch){
		LsManager.getInstance().check();
		ModelAndView mView = MvUtils.getView("/riskevent/EventList");
		
		if(StringUtils.isBlank(eventInputModel.getStartRiskDate())){
			eventInputModel.setStartRiskDate(DateUtil.getCurrDayStart());
		}
		if(StringUtils.isBlank(eventInputModel.getEndRiskDate())){
            eventInputModel.setEndRiskDate(DateUtil.getCurrDayEnd());
		}
		if(eventInputModel.getPageSize() <= 0){
            eventInputModel.setPageSize(15);
        }
        if(eventInputModel.getCurrentPage() <= 0){
            eventInputModel.setCurrentPage(1);
        }

		//查询参数
		mView.addObject("eventInputModel", eventInputModel);
		mView.addObject("deepSearch", deepSearch);
		mView.addObject("extend", eventInputModel.getExtendMap());
		Map<String, Object> map = monitorService.getEventList(eventInputModel);
		long totalSize = (Long)map.get("totalSize");
		mView.addObject("totalPages",(totalSize%eventInputModel.getPageSize()==0)?
                (totalSize/eventInputModel.getPageSize()):(totalSize/eventInputModel.getPageSize()+1));
		mView.addObject("totalCount",totalSize);
		mView.addObject("allScenes",  scenesService.selectAll());
		mView.addObject("list", (List<Map<String, Object>>)map.get("list")); //数据列表
		return mView;
	}

	@RequestMapping("/eventDetailDiv")
	public ModelAndView eventDetailDiv(@RequestParam(value="riskId",required=true) String riskId,
									@RequestParam(value="riskDate",required=true) String riskDate){
		LsManager.getInstance().check();
		Map<String,Object> resultMap = monitorService.getEventDetail(riskId, riskDate);
		String viewName = "/riskevent/part/AntiFraudEventDiv";
		if( MonitorService.RISK_BUSI_TYPE_DT.equals(resultMap.get("riskBusiType"))){
			viewName = "/riskevent/part/DecisionTreeEventDiv";
		}else if( MonitorService.RISK_BUSI_TYPE_SC.equals(resultMap.get("riskBusiType"))){
			viewName = "/riskevent/part/SCardEventDiv";
		}
		ModelAndView mView = MvUtils.getView(viewName);
		mView.addAllObjects(resultMap);
		return mView;
	}

	@RequestMapping("/relationshipDiv")
	public ModelAndView relationshipDiv(@RequestParam(value="uid",required=true) String uid,
									    @RequestParam(value="deviceFingerprint",required=false) String deviceFingerprint,
                                        @RequestParam(value="riskDate",required=false) String riskDate){
		LsManager.getInstance().check();
		ModelAndView mView = MvUtils.getView("/riskevent/part/AssociationAnalysisDiv");
		mView.addObject("deviceFingerprints", monitorService.getRelationship("uid", uid,riskDate));
        Map<String,Long> map = Collections.emptyMap();
        if ( deviceFingerprint != null && ( !"".equals(deviceFingerprint )) ){
            map = monitorService.getRelationship("deviceFingerprint", deviceFingerprint,riskDate);
        }
        mView.addObject("riskDate", riskDate);
		mView.addObject("uids", map);
		return mView;
	}

	@RequestMapping("/ruleList")
	public ModelAndView ruleList(@RequestParam(value="sceneNo",required=false)  String sceneNo,
			@RequestParam(value="searchDate",required=false) String searchDate){
	
		ModelAndView mView = MvUtils.getView("/riskevent/RuleList");
		LsManager.getInstance().check();
		List<Scenes> allScenes = scenesService.selectAll();
		if(StringUtils.isBlank(searchDate)){
			searchDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		}
		if(StringUtils.isBlank(sceneNo)){ //默认选中第一个场景
			sceneNo = allScenes.isEmpty()?"":allScenes.get(0).getSceneNo();
		}
		//场景号向同盾兼容
		mView.addObject("sceneNo",sceneNo);
		mView.addObject("searchDate", searchDate);
		mView.addObject("allScenes", allScenes);
		mView.addObject("list", monitorService.getRuleList(sceneNo, searchDate)); 
		return mView;
	}
}
