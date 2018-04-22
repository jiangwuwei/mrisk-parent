package com.zoom.risk.operating.scard.proxy;


import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.scard.model.JadeMode;
import com.zoom.risk.operating.scard.model.JadeModeDefinition;
import com.zoom.risk.operating.scard.model.JadeModeVo;
import com.zoom.risk.operating.scard.service.JadeModeDefinitionService;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("jadeServiceProxy")
public class JadeServiceProxy {
    @Resource
    private JadeModeDefinitionService jadeModeDefinitionService;
    @Resource
    SessionManager sessionManager;

    public JadeMode selectModelByPrimaryKey(Integer id){
        LsManager.getInstance().check();
        return sessionManager.runWithSession(()->jadeModeDefinitionService.selectModelByPrimaryKey(id), DBSelector.JADE_MASTER_DB);
    }
    public List<JadeMode> selectModeByApplyType(){
        return sessionManager.runWithSession(()->jadeModeDefinitionService.selectModeByApplyType(), DBSelector.JADE_MASTER_DB);
    }
    public void insertMode(JadeMode jadeMode){
        LsManager.getInstance().check();
        sessionManager.runWithSession(()->jadeModeDefinitionService.insertMode(jadeMode), DBSelector.JADE_MASTER_DB);
    }
    public void updateMode(JadeMode jadeMode){
        sessionManager.runWithSession(()->jadeModeDefinitionService.updateMode(jadeMode), DBSelector.JADE_MASTER_DB);
    }
    public void deleteMode(Integer id){
        sessionManager.runWithSession(()->jadeModeDefinitionService.deleteMode(id), DBSelector.JADE_MASTER_DB);
    }


    public List<JadeModeDefinition> selectAllScardDefinition(){
        return  sessionManager.runWithSession(()->jadeModeDefinitionService.selectAllScardDefinition(), DBSelector.JADE_MASTER_DB);
    }

    public JadeModeDefinition selectDefinitionByPrimaryKey(Long id){
        return  sessionManager.runWithSession(()->jadeModeDefinitionService.selectDefinitionByPrimaryKey(id), DBSelector.JADE_MASTER_DB);
    }
    public List<JadeModeDefinition> selectDefinitionByModelType(Integer typeId){
        return  sessionManager.runWithSession(()->jadeModeDefinitionService.selectDefinitionByModelType(typeId), DBSelector.JADE_MASTER_DB);
    }
    public void deleteDefinition(Long id){
        sessionManager.runWithSession(()->jadeModeDefinitionService.deleteDefinition(id), DBSelector.JADE_MASTER_DB);
    }
    public void insertDefinition(JadeModeDefinition jadeModeDefinition){
        sessionManager.runWithSession(()->jadeModeDefinitionService.insertDefinition(jadeModeDefinition), DBSelector.JADE_MASTER_DB);
    }
    public void updateDefinition(JadeModeDefinition jadeModeDefinition){
        sessionManager.runWithSession(()->jadeModeDefinitionService.updateDefinition(jadeModeDefinition), DBSelector.JADE_MASTER_DB);
    }

    public List<JadeModeVo> findAllParams(){
        LsManager.getInstance().check();
        final List<JadeModeVo> resultList = new ArrayList<>();
        sessionManager.runWithSession(()-> {
            List<JadeModeDefinition> definitions = jadeModeDefinitionService.selectAllScardDefinition();
            List<JadeMode> list = jadeModeDefinitionService.selectModeByApplyType();
            final Map<String,JadeModeVo> map = new HashMap<>();
            list.forEach( jade -> {
                JadeModeVo vo = new JadeModeVo();
                vo.setId(jade.getId());
                vo.setTypeName(jade.getTypeName());
                resultList.add(vo);
                map.put(jade.getId()+"", vo);
            });
            definitions.forEach( jadeModeDefinition ->{
                map.get(jadeModeDefinition.getTypeId()+"").addJadeModeDefinition(jadeModeDefinition);
            });
        }, DBSelector.JADE_MASTER_DB);
        return resultList;
    }

}
