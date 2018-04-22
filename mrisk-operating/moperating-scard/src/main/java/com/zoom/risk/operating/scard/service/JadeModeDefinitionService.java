package com.zoom.risk.operating.scard.service;

import com.zoom.risk.operating.scard.dao.JadeModeDefinitionMapper;
import com.zoom.risk.operating.scard.model.JadeMode;
import com.zoom.risk.operating.scard.model.JadeModeDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("jadeModeDefinitionService")
@Transactional
public class JadeModeDefinitionService {
    @Resource
    private JadeModeDefinitionMapper jadeModeDefinitionMapper;

    public JadeMode selectModelByPrimaryKey(Integer id){
        return  jadeModeDefinitionMapper.selectModelByPrimaryKey(id);
    }
    public List<JadeMode> selectModeByApplyType(){
        return  jadeModeDefinitionMapper.selectModeByApplyType();
    }
    public void insertMode(JadeMode jadeMode){
        jadeModeDefinitionMapper.insertMode(jadeMode);
    }
    public void updateMode(JadeMode jadeMode){
        jadeModeDefinitionMapper.updateMode(jadeMode);
    }
    public void deleteMode(Integer id){
        jadeModeDefinitionMapper.deleteMode(id);
    }


    public List<JadeModeDefinition> selectAllScardDefinition(){
        return jadeModeDefinitionMapper.selectAllScardDefinition();
    }

    public JadeModeDefinition selectDefinitionByPrimaryKey(Long id){
        return  jadeModeDefinitionMapper.selectDefinitionByPrimaryKey(id);
    }
    public List<JadeModeDefinition> selectDefinitionByModelType(Integer typeId){
        return jadeModeDefinitionMapper.selectDefinitionByModelType(typeId);
    }
    public void deleteDefinition(Long id){
        jadeModeDefinitionMapper.deleteDefinition(id);
    }
    public void insertDefinition(JadeModeDefinition jadeModeDefinition){
        jadeModeDefinitionMapper.insertDefinition(jadeModeDefinition);
    }
    public void updateDefinition(JadeModeDefinition jadeModeDefinition){
        jadeModeDefinitionMapper.updateDefinition(jadeModeDefinition);
    }


}
