package com.zoom.risk.operating.scard.dao;

import com.zoom.risk.operating.scard.model.SCardParamRoute;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="scardParamRouteMapper")
public interface SCardParamRouteMapper {

    List<SCardParamRoute> getRoutesBySCard(Long scardId);

    List<SCardParamRoute> getRoutesByParam(Long paramId);

    void deleteByPrimaryKey(Long paramId);

    Long saveParamRoute(SCardParamRoute route);

    void updateByPrimaryKey(SCardParamRoute route);


}