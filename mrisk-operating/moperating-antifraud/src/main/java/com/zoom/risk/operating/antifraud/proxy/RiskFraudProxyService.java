package com.zoom.risk.operating.antifraud.proxy;

import com.zoom.risk.operating.antifraud.vo.Device;
import com.zoom.risk.operating.antifraud.vo.Event;
import com.zoom.risk.operating.antifraud.vo.EventQuantity;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

public interface RiskFraudProxyService {
	
	public List<EventQuantity> getEventQuantityToday(String platform);
	
	public List<Device> getDeviceQuantity(Map<String, Object> paramMap);
	
	public List<Event> getEventQuantity(Map<String, Object> paramMap);

}
