package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zoom.risk.operating.ruleconfig.common.CommonUtils;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.dao.ScenesMapper;
import com.zoom.risk.operating.ruleconfig.model.Scenes;
import com.zoom.risk.operating.ruleconfig.vo.SceneVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scenesService")
public class ScenesService {
	
	private static final Logger logger  = LogManager.getLogger(ScenesService.class);
	
	@Autowired
	private ScenesMapper scenesMapper;
	@Autowired
	private SceneConfigService sceneConfigService;

	public Scenes selectById(String sceneNo){
		try {
			return scenesMapper.selectByPrimaryKey(sceneNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public List<Scenes> selectByType(Integer sceneType){
	    return scenesMapper.selectByType(sceneType);
    }

	/**
	 *
	 * @return
	 */
	public List<Scenes> selectAll(){
		return scenesMapper.selectByType(null);
	}
	
	public List<Scenes> selectPage(String sceneNo, int offset, int limit, Integer sceneType){
		try {
			Map<String, Object> pageParas = new HashMap<>();
			pageParas.put("sceneNo", sceneNo);
			pageParas.put("offset", offset);
			pageParas.put("limit", limit);
			pageParas.put("sceneType", sceneType);
			return scenesMapper.selectPage(pageParas);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public int selectCount(String sceneNo, Integer sceneType){
		try {
			return scenesMapper.selectCount(sceneNo, sceneType);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}
	
	public boolean update(Scenes scenes){
		try {
			scenesMapper.updateByPrimaryKeySelective(scenes);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public ResultCode delById(String sceneNo){
		try {
			scenesMapper.deleteByPrimaryKey(sceneNo);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean insert(Scenes sceneNo) {
		try {
			scenesMapper.insert(sceneNo);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	
	public boolean existScene(String sceneNo, String name) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("sceneNo", sceneNo);
		map.put("name", name);
		return scenesMapper.existScene(map)==0?false:true;
	}

	/**
	 * 调用jade api获取场景属性：中文名和英文名
	 * @param sceneNo
	 * @param excludedAttrList 需要排除的属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SceneVo> getSceneVoList(String sceneNo, List<String> excludedAttrList,
                                        Map<String, Object> originalData, List<String> priorityAttrList){
		List<Map<String, Object>> configInfo = sceneConfigService.getSceneNameMap(sceneNo);
		List<SceneVo> list = Lists.newArrayList();
		Map<String, SceneVo> priorityVoMap = Maps.newHashMap();
        List<SceneVo> tempList = Lists.newArrayList();
		for (Map<String, Object> map : configInfo) {
			String engName = (String)map.get("param_name");
			if(excludedAttrList.indexOf(engName) == -1 && (originalData.get(engName) != null)){
				String param_value = String.valueOf(originalData.get(engName));
				if("riskStatus".equals(engName)){//风控业务流水号特殊处理
					int riskStatus = Integer.valueOf(param_value).intValue();
					param_value = riskStatus==0?"初始状态":(riskStatus==1?"通过":"失败");
				}
				if("identityType".equals(engName)){//证件类型特殊处理
					param_value = CommonUtils.getCertiName(param_value);
				}
				if(priorityAttrList.contains(engName)){
                    priorityVoMap.put(engName,new SceneVo((String)map.get("param_name"),(String)map.get("chinese_name"),param_value));
                }else{
                    tempList.add(new SceneVo((String)map.get("param_name"),(String)map.get("chinese_name"),param_value));
                }
			}
			/*
			//申购类型翻译
			if ( "011401".equals(originalData.get("scene")) && "productType".equals(engName) ){
				tempList.add(new SceneVo("productName","申购类型名称", MonitorService.productTypeMap.get(originalData.get(engName)+"")));
			}
			*/
		}
		/*
        for (String string : priorityAttrList) {
		    if(priorityVoMap.get(string)==null){
		        if("deviceFingerprint".equals(string)){
		            list.add(new SceneVo(string,"同盾设备指纹",null));
                }
                if("deviceFpip".equals(string)){
		            list.add(new SceneVo(string,"设备指纹采集IP",null));
                }
            }else{
                list.add(priorityVoMap.get(string));
            }
        }
        */
        list.addAll(tempList);
		return list;
	}
}
