package com.zoom.risk.operating.web.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.web.vo.SceneDefinitionVo;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2017/2/25.
 */
@Service("sceneDefinitionService")
public class SceneDefinitionService {

    private static final Logger logger = LogManager.getLogger(SceneDefinitionService.class);

    private static String SCENE_DEFINITION_SQL =  "select id,scene_no,scene_name from  jade_scene_definition";

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<SceneDefinitionVo> getSceneDefinitionVoList(String sceneNo){
        List<Map<String, Object>> dataList = getSceneDefinitionMap(sceneNo);
        List<SceneDefinitionVo> voList = Lists.newArrayList();
        for (Map<String, Object> map: dataList){
            voList.add(new SceneDefinitionVo((Long)map.get("id"), (String)map.get("scene_no"), (String)map.get("scene_name")));
        }
        return voList;
    }

    /**
     * 获取指定类型的场景属性
     * @param sceneNo
     * @return
     */
    public List<Map<String, Object>> getSceneDefinitionMap(String sceneNo){
        try {
            String sqlFormat = SCENE_DEFINITION_SQL;
            if(StringUtils.isNotBlank(sceneNo)){
                final String finalSqlFormat = sqlFormat + " where scene_no = %s";
                return sessionManager.runWithSession( ()-> jdbcTemplate.queryForList(String.format(finalSqlFormat,sceneNo)) , DBSelector.JADE_MASTER_DB);
            }
            final String finalSqlFormat = sqlFormat + " order by scene_no asc";
            return sessionManager.runWithSession( ()-> jdbcTemplate.queryForList(String.format(finalSqlFormat,sceneNo)) , DBSelector.JADE_MASTER_DB);
        } catch (Exception e) {
            logger.error(e);
            logger.error("failed to invoke jadeDataApi!");
        }
        return Lists.newArrayList();
    }

}
