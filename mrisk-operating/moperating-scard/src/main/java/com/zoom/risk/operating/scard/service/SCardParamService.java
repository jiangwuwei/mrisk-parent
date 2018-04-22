package com.zoom.risk.operating.scard.service;

import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import com.zoom.risk.operating.ruleconfig.service.RiskNumberService;
import com.zoom.risk.operating.ruleconfig.service.SceneConfigService;
import com.zoom.risk.operating.scard.dao.SCardMapper;
import com.zoom.risk.operating.scard.dao.SCardParamMapper;
import com.zoom.risk.operating.scard.dao.SCardParamRouteMapper;
import com.zoom.risk.operating.scard.model.SCardParam;
import com.zoom.risk.operating.scard.model.SCardParamRoute;
import com.zoom.risk.operating.scard.model.SCardParamVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("scardParamService")
public class SCardParamService {
	private static final Logger logger  = LogManager.getLogger(SCardParamService.class);
	@Autowired
	private SCardMapper scardMapper;
	@Autowired
	private RiskNumberService riskNumberService;
	@Autowired
	private SCardParamMapper scardParamMapper;
	@Autowired
	private SceneConfigService sceneConfigService;
	@Autowired
	private SCardParamRouteService scardParamRouteService;
	@Autowired
	private SCardParamRouteMapper scardParamRouteMapper;

	@Transactional(readOnly = true)
	public boolean alreadyRefered(String paramName){
		Integer count = scardParamMapper.findReferanceCount(paramName);
		boolean result = false;
		if ( count != null && count > 0 ){
			result = true;
		}
		return result;
	}
	/**
	 * 评分卡变量&路径查询
	 * @param scardId
	 * @return
	 */
	public List<SCardParamVo> getParamsGroupByType(Long scardId){
		Map<String,String> keyValueMap = sceneConfigService.getScardTypes();
		List<SCardParam> list = this.getSCardParams(scardId);
		List<SCardParamRoute> routesList = scardParamRouteService.getParamRoutes(scardId);
		for( SCardParam param : list ){
			for( SCardParamRoute route : routesList ){
				if ( param.getId().longValue() ==  route.getParamId().longValue()){
					param.addRoute(route);
				}
			}
		}
		Integer typeId = null;
		Integer preTypeId = null;
		SCardParamVo vo = null;
		List<SCardParamVo> resultList = new ArrayList<>();
		for( SCardParam param : list ){
			typeId = param.getTypeId();
			if ( preTypeId == null || typeId.intValue() != preTypeId.intValue() ){
				vo = new SCardParamVo(param.getTypeId(),keyValueMap.get(param.getTypeId()+""));
				resultList.add(vo);
				vo.add(param);
			}else{
				vo.add(param);
			}
			preTypeId =  typeId;
		}
		return resultList;
	}

	/**
	 * 为评分卡选择参数
	 * @param scardId
	 * @return
	 */
	public List<SCardParamVo> getSelectParamsGroupByType(Long scardId){
		Map<String,String> keyValueMap = sceneConfigService.getScardTypes();
		//List<SCardParam> list = this.getSCardParams(scardId);
		List<Map<String,Object>> paramsList = sceneConfigService.getScardParams();
		List<String> usedParamIds = scardParamMapper.findUsedParams(scardId);
		Long typeId = null;
		Long preTypeId = null;
		SCardParamVo vo = null;
		List<SCardParamVo> resultList = new ArrayList<>();
		for( Map<String,Object> param : paramsList ){
			param.put("checked",false);
			String paramName = (String)param.get("param_name");
			typeId = (Long)param.get("type_id");
			boolean alreadyFlag = false;
			for( String v : usedParamIds ){
				if ( v.equals(paramName)){
					param.put("checked",true);
					alreadyFlag = true;
					break;
				}
			}
			//if ( !alreadyFlag )
			{
				if (preTypeId == null || typeId.intValue() != preTypeId.intValue()) {
					vo = new SCardParamVo(typeId.intValue(), (String) keyValueMap.get(typeId + ""));
					resultList.add(vo);
					vo.add(param);
				} else {
					vo.add(param);
				}
				preTypeId = typeId;
			}
		}
		return resultList;
	}

	@Transactional
	public void saveParams(List<Map<String,Object>> paramsList, Long scardId, Long[] selectParams){
		String sceneNo = scardMapper.selectByPrimaryKey(scardId).getSceneNo();
		List<SCardParam> list = new ArrayList<>();
		for(Long paramId : selectParams ){
			for(Map<String,Object> map : paramsList ){
				if ( ((Long)map.get("id")).longValue() == paramId ) {
					SCardParam param = new SCardParam();
					param.setParamNo(riskNumberService.getJYLRiskNumber(RiskNumber.SCARD_CLASS_PARAM, sceneNo));
					param.setChineseName((String)map.get("chinese_name"));
					param.setDbType((Integer)map.get("db_type"));
					param.setScardId(scardId);
					param.setParamName((String)map.get("param_name"));
					param.setDefaultScore(1F);
					param.setTypeId(((Long)map.get("type_id")).intValue());
					list.add(param);
				}
			}
		}
		scardParamMapper.batchSaveParams(list);
	}

	@Transactional
	public List<SCardParam> getSCardParams(Long scardId){
		return scardParamMapper.getSCardParams(scardId);
	}

	public void deleteByPrimaryKey(Long paramId){
		scardParamMapper.deleteByPrimaryKey(paramId);
	}

	public void updateByPrimaryKey(SCardParam route){
		scardParamMapper.updateByPrimaryKey(route);
	}

	public SCardParam findScardParamAndRoutes(Long paramId){
		SCardParam sCardParam = scardParamMapper.selectPrimaryKey(paramId);
		sCardParam.setRouteList(scardParamRouteMapper.getRoutesByParam(paramId));
		return sCardParam;
	}

	public SCardParam selectPrimaryKey(Long paramId){
		return scardParamMapper.selectPrimaryKey(paramId);
	}
}
