package com.zoom.risk.operating.scard.dao;

import com.zoom.risk.operating.scard.model.JadeMode;
import com.zoom.risk.operating.scard.model.JadeModeDefinition;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="jadeModeDefinitionMapper")
public interface JadeModeDefinitionMapper {

    JadeMode selectModelByPrimaryKey(Integer id);
    List<JadeMode> selectModeByApplyType();
    void insertMode(JadeMode jadeMode);
    void updateMode(JadeMode jadeMode);
    void deleteMode(Integer id);

    JadeModeDefinition selectDefinitionByPrimaryKey(Long id);
    List<JadeModeDefinition> selectDefinitionByModelType(Integer typeId);
    void deleteDefinition(Long id);
    void insertDefinition(JadeModeDefinition jadeModeDefinition);
    void updateDefinition(JadeModeDefinition jadeModeDefinition);
    List<JadeModeDefinition> selectAllScardDefinition();


}
