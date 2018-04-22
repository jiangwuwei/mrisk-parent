package com.zoom.risk.operating.antifraud.service;

import com.zoom.risk.operating.antifraud.vo.Device;
import com.zoom.risk.operating.antifraud.vo.Event;
import com.zoom.risk.operating.antifraud.vo.EventQuantity;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 13, 2015
 */

public interface RiskFraudService {
	
	public List<EventQuantity> getEventQuantityToday(String platform);
	
	public List<Device> getDeviceQuantity(Map<String ,Object> paramMap);
	
	public List<Event> getEventQuantity(Map<String ,Object> paramMap);

}
