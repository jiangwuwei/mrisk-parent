package com.zoom.risk.operating.antifraud.dao;

import com.zoom.risk.operating.antifraud.vo.DeviceQuantity;
import com.zoom.risk.operating.antifraud.vo.EventQuantity;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

/**
 * 
 * @author JiangMi,Mar,17,2017
 *
 */

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@ZoomiBatisRepository(value="riskMonitorMapper")
public interface RiskFraudMapper {
	
	public List<EventQuantity> getEventQuantityToday(Map<String,Object> paramMap);
	
	public List<DeviceQuantity> getDeviceQuantity(Map<String ,Object> paramMap);
	
	public List<EventQuantity> getEventQuantity(Map<String,Object> paramMap);
	

}
