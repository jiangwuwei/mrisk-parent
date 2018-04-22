package com.zoom.risk.operating.scard.dao;

import com.zoom.risk.operating.scard.model.SCardParam;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="scardParamMapper")
public interface SCardParamMapper {

    int batchSaveParams(@Param("paramsList") List<SCardParam> paramsList);

    void insertParams(SCardParam scardParam);

    List<SCardParam> getSCardParams(Long scardId);

    void deleteByPrimaryKey(Long paramId);

    void updateByPrimaryKey(SCardParam route);

    SCardParam selectPrimaryKey(Long paramId);


    Integer findReferanceCount(String paramName);

    List<String> findUsedParams(Long scardId);

}