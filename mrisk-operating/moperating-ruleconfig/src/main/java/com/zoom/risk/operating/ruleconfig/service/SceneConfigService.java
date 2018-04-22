package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.SceneConstants;
import com.zoom.risk.operating.ruleconfig.vo.ConditionSceneConfigVo;
import com.zoom.risk.operating.ruleconfig.vo.PolicyRouterSceneVo;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2017/2/25.
 */
@Service("sceneConfigService")
public class SceneConfigService {

    private static final Logger logger = LogManager.getLogger(SceneConfigService.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    private static String SCENE_CONFIG_SQL =  "SELECT config.chinese_name, config.param_name,config.db_name, config.db_type "+
            " FROM jade_scene_config config INNER JOIN jade_scene_definition definition "+
            " ON config.scene_id = definition.id AND scene_no = '%s'";

    private static final String SCENE_EXTEND_SQL ="select chinese_name, param_name, param_name as db_name, data_type as db_type FROM zoom_dim_extend_attribute ";

    private static final String SCARD_PARAMS_SQL = "select m.id, m.type_id, m.chinese_name, m.param_name, m.db_name, m.db_type from jade_model_definition m where m.type_id in ( select id from jade_model_type where apply_type = 2 ) order by type_id ";

    private static final String SCARD_MODE_TYPE_SQL = "select id, type_name from jade_model_type where apply_type = 2 ";

    /**
     * 调用jade api获取场景属性：中文名和英文名
     * @param sceneNo
     * @return
     */
    public List<Pair<String, String>> getSceneNamePair(String sceneNo){
        List<Pair<String, String>> list = Lists.newArrayList();
        for (Map<String, Object> map : getSceneNameMap(sceneNo)) {
            list.add(Pair.of((String)map.get("chinese_name"), (String)map.get("param_name")));
        }
        return list;
    }


    public List<Map<String,Object>> getScardParams(){
        return sessionManager.runWithSession( ()-> jdbcTemplate.queryForList(SCARD_PARAMS_SQL) , DBSelector.JADE_MASTER_DB);
    }


    public Map<String,String> getScardTypes(){
        List<Map<String,Object>>  list = sessionManager.runWithSession( ()-> jdbcTemplate.queryForList(SCARD_MODE_TYPE_SQL) , DBSelector.JADE_MASTER_DB);
        Map<String,String> keyValueMap = new HashMap<>();
        for (Map<String, Object> stringObjectMap : list) {
            keyValueMap.put(String.valueOf(stringObjectMap.get("id")), (String)stringObjectMap.get("type_name"));
        }
        return keyValueMap;
    }

    /**
     * 调用jade api获取场景指定数据类型的属性：中文名和英文名
     * @param sceneNo
     * @param dataType 详见jade库维表
     * @return
     */
    public List<Pair<String, String>> getSceneNamePair(String sceneNo, int dataType){
        List<Pair<String, String>> list = Lists.newArrayList();
        for (Map<String, Object> map : getSceneNameMap(sceneNo, dataType)) {
            list.add(Pair.of((String)map.get("chinese_name"), (String)map.get("param_name")));
        }
        return list;
    }

    public List<ConditionSceneConfigVo> getCondSceneConfVo(String sceneNo){
        List<Map<String,Object>> sceneConfigList = getSceneNameMap(sceneNo);
        List<ConditionSceneConfigVo> list = Lists.newArrayList();
        for (Map<String,Object> map : sceneConfigList) {
            list.add(new ConditionSceneConfigVo((String)map.get("chinese_name"),
                    (String)map.get("param_name"), getSceneDataTypeName((Integer)map.get("db_type"))));
        }
        return list;
    }

    public List<PolicyRouterSceneVo> getPolicyRouterVo(String sceneNo){
        List<Map<String,Object>> sceneConfigList = getSceneNameMap(sceneNo);
        List<PolicyRouterSceneVo> list = Lists.newArrayList();
        for (Map<String,Object> map : sceneConfigList) {
            list.add(new PolicyRouterSceneVo((String)map.get("chinese_name"),
                    (String)map.get("param_name"),(Integer)map.get("db_type")));
        }
        return list;
    }

    public List<Map<String, Object>> getSceneNameMap(String sceneNo){
        return getSceneNameMap(sceneNo,-1);
    }

    /**
     * 获取场景属性名对应的数据库名
     * @param sceneNo
     * @return
     */
    public Map<String, String> getDbNameMap(String sceneNo){
        Map<String, String> resultMap = Maps.newHashMap();
        for (Map<String, Object> map : getSceneNameMap(sceneNo)) {
            resultMap.put((String)map.get("param_name"), (String)map.get("db_name"));
        }
        return resultMap;
    }

    /**
     * 获取指定类型的场景属性
     * @param sceneNo
     * @param dataType 详见jade库维表
     * @return
     */
    public List<Map<String, Object>> getSceneNameMap(String sceneNo, int dataType){
        List<Map<String, Object>> result = null;
        try {
            final String sqlFormat = SCENE_CONFIG_SQL;
            if(dataType>0){
                final String finalSqlFormat = sqlFormat + " and db_type = %s";
                result = sessionManager.runWithSession( ()-> jdbcTemplate.queryForList(String.format(finalSqlFormat,sceneNo, dataType)) , DBSelector.JADE_MASTER_DB);
            }else {
                result = sessionManager.runWithSession( ()-> jdbcTemplate.queryForList(String.format(sqlFormat,sceneNo, dataType)) , DBSelector.JADE_MASTER_DB);
            }
            List<Map<String, Object>> extendList = sessionManager.runWithSession( ()-> jdbcTemplate.queryForList(SCENE_EXTEND_SQL) , DBSelector.OPERATING_MASTER_DB);
            result.addAll(extendList);
        } catch (Exception e) {
            result = Collections.emptyList();
            logger.error(e);
            logger.error("failed to invoke jadeDataApiService!");
        }
        return result;
    }

    /**
     *
     * @param dataType
     * @return
     */
    private String getSceneDataTypeName(int dataType){
        String dataTypeName = "";
        switch (dataType){
            case SceneConstants.DB_TYPE_VARCHAR:
            case SceneConstants.DB_TYPE_CHAR:
                dataTypeName = Constants.DATA_TYPE_STRING;
                break;
            case SceneConstants.DB_TYPE_INT:
            case SceneConstants.DB_TYPE_BIGINT:
                dataTypeName = Constants.DATA_TYPE_NUMBER;
                break;
            case SceneConstants.DB_TYPE_DECIMAL:
                dataTypeName = Constants.DATA_TYPE_MONEY;
                break;
            case SceneConstants.DB_TYPE_DATETIME:
            case SceneConstants.DB_TYPE_TIMESTAME:
            case SceneConstants.DB_TYPE_DATE:
                dataTypeName = Constants.DATA_TYPE_DATE;
                break;
            default:
                dataTypeName = "错误";
        }
        return dataTypeName;
    }
}
