package com.zoom.risk.operating.scard.service;

import com.zoom.risk.operating.scard.dao.SCardParamRouteMapper;
import com.zoom.risk.operating.scard.model.SCardParamRoute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("scardParamRouteService")
public class SCardParamRouteService {
	private static final Logger logger  = LogManager.getLogger(SCardParamRouteService.class);

	@Autowired
	private SCardParamRouteMapper scardParamRouteMapper;


	public List<SCardParamRoute> getParamRoutes(Long scardId){
 		return  scardParamRouteMapper.getRoutesBySCard(scardId);
	}

	public void deleteByPrimaryKey(Long paramId){
		scardParamRouteMapper.deleteByPrimaryKey(paramId);
	}

	public Long saveParamRoute(SCardParamRoute route){
		return scardParamRouteMapper.saveParamRoute(route);
	}

	public void updateByPrimaryKey(SCardParamRoute route){
		scardParamRouteMapper.updateByPrimaryKey(route);
	}
}
