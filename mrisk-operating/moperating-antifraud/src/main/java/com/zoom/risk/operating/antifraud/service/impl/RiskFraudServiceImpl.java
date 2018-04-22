package com.zoom.risk.operating.antifraud.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.operating.antifraud.dao.RiskFraudMapper;
import com.zoom.risk.operating.antifraud.service.RiskFraudService;
import com.zoom.risk.operating.antifraud.vo.Device;
import com.zoom.risk.operating.antifraud.vo.DeviceQuantity;
import com.zoom.risk.operating.antifraud.vo.Event;
import com.zoom.risk.operating.antifraud.vo.EventQuantity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;

/**
 * @author jiangyulin, Mar, 13, 2015
 */

@Service("riskFraudService")
public class RiskFraudServiceImpl implements RiskFraudService {
	private static final Logger logger = LogManager.getLogger(RiskFraudServiceImpl.class);
	private static final String[] decisionCodes = { "1", "2", "3" };

	@Resource(name = "riskFraudMapper")
	private RiskFraudMapper riskFraudMapper;

	@Override
	public List<EventQuantity> getEventQuantityToday(String platform) {
		Map<String, Object> map = new HashMap<>();
		map.put("platform", platform);
		List<EventQuantity> list = riskFraudMapper.getEventQuantityToday(map);
		int totalCount = 0;
		EventQuantity event = new EventQuantity();
		// decisonCode为4表示所有总量
		event.setDecisionCode("4");
		for (EventQuantity e : list) {
			totalCount = totalCount + e.getQuantity();
		}
		event.setQuantity(totalCount);
		list.add(event);
		for (int i = 0; i < decisionCodes.length; i++) {
			boolean exists = false;
			for (EventQuantity e : list) {
				if (e.getDecisionCode().equals(decisionCodes[i])) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				EventQuantity addEvent = new EventQuantity(decisionCodes[i], 0);
				list.add(addEvent);
			}
		}
		return list;
	}

	public List<Device> getDeviceQuantity(Map<String, Object> paramMap) {
		List<DeviceQuantity> list = riskFraudMapper.getDeviceQuantity(paramMap);
		logger.info("deviceList:" + JSON.toJSONString(list));
		Set<Device> deviceList = new HashSet<>();
		Map<String, Device> devMap = new HashMap<>();

		for (DeviceQuantity d : list) { // 将获取到的数据分类，日期、设备获取成功量、失败量按日期顺序分别添加到一个list里
			Device dc = devMap.get(d.getDateStr());
			if ( dc == null ){
				dc = new Device(d.getDateStr());
				devMap.put(d.getDateStr(), dc);
			}
			if ( "1".equals(d.getDevice())){
				dc.setSuccessCount(d.getQuantity());
			}else if ("0".equals(d.getDevice())){
				dc.setFailCount(d.getQuantity());
			}
		}
		List<Device> resltLst = new ArrayList<>();
		devMap.values().forEach((d)-> resltLst.add(d));
		resltLst.sort( (a,b)-> a.getDateStr().compareTo(b.getDateStr()));
		return resltLst;
	}

	@Override
	public List<Event> getEventQuantity(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<EventQuantity> list = riskFraudMapper.getEventQuantity(paramMap);
		List<Event> eventList = new ArrayList<>();
		String previousDateStr = null;
		Event event = null;
		for (EventQuantity e : list) { // 将获取到的数据分类，日期、通过量，拒绝量，人工审核量按日期顺序添加到一个list里
			if (!e.getDateStr().equals(previousDateStr)) {
				event = new Event();
				eventList.add(event);
				previousDateStr = e.getDateStr();
				event.setDateStr(previousDateStr);
				if ("1".equals(e.getDecisionCode())) {
					event.setPassCount(e.getQuantity());
				} else if ("2".equals(e.getDecisionCode())) {
					event.setVerifyCount(e.getQuantity());
				} else if ("3".equals(e.getDecisionCode())) {
					event.setRefuseCount(e.getQuantity());
				}
			}else{
				if ("1".equals(e.getDecisionCode())) {
					event.setPassCount(e.getQuantity());
				} else if ("2".equals(e.getDecisionCode())) {
					event.setVerifyCount(e.getQuantity());
				} else if ("3".equals(e.getDecisionCode())) {
					event.setRefuseCount(e.getQuantity());
				}
			}

		}

		return eventList;
	}

}
