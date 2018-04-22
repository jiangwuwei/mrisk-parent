package com.zoom.risk.operating.antifraud.proxy.impl;

import com.zoom.risk.operating.antifraud.proxy.RiskFraudProxyService;
import com.zoom.risk.operating.antifraud.service.RiskFraudService;
import com.zoom.risk.operating.antifraud.vo.Device;
import com.zoom.risk.operating.antifraud.vo.Event;
import com.zoom.risk.operating.antifraud.vo.EventQuantity;
import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

@Service("riskFraudProxyService")
public class RiskFraudProxyServiceImpl implements RiskFraudProxyService {
	private static final String[] decisionCodes = { "1", "2", "3" };

	@Resource(name = "riskFraudService")
	private RiskFraudService riskFraudService;


	@Resource(name = "sessionManager")
	private SessionManager sessionManager;

	@Override
	public List<EventQuantity> getEventQuantityToday(String platform) {
		return sessionManager.runWithSession(() -> riskFraudService.getEventQuantityToday(platform), DBSelector.JADE_MASTER_DB);
}

	public List<Device> getDeviceQuantity(Map<String, Object> paramMap) {
        return sessionManager.runWithSession(() -> riskFraudService.getDeviceQuantity(paramMap), DBSelector.JADE_MASTER_DB);
	}

	@Override
	public List<Event> getEventQuantity(Map<String, Object> paramMap) {
        return sessionManager.runWithSession(() -> riskFraudService.getEventQuantity(paramMap), DBSelector.JADE_MASTER_DB);
	}

}
