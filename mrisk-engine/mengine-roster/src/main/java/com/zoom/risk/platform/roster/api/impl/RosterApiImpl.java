package com.zoom.risk.platform.roster.api.impl;

import com.zoom.risk.platform.roster.api.RosterApi;
import com.zoom.risk.platform.roster.api.RosterInnerApiService;
import com.zoom.risk.platform.roster.vo.Rosters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/4/5.
 */
@Service("rosterApi")
public class RosterApiImpl implements RosterApi {
	private static final Logger logger = LogManager.getLogger(RosterApiImpl.class);
	//uid:1  mobile:2 idnumber:3 cardnumber:4
	private static final String[] RISK_KEY_UID = {"uid"};
	private static final String[] RISK_KEY_MOBILE = {"mobile", "cardBindingMobile"};  //"accountMobile",
	private static final String[] RISK_KEY_IDNUMBER = {"idCardNumber"};
	private static final String[] RISK_KEY_CARDNUMBER = {"bankCardNumber"};
	private static final String[][] ALL_KEYS = { RISK_KEY_UID, RISK_KEY_MOBILE,  RISK_KEY_IDNUMBER, RISK_KEY_CARDNUMBER };
	private static final Map<String,String> nameValueMapping = new HashMap<>();

	static{
		nameValueMapping.put(RISK_KEY_UID[0],"1");
		nameValueMapping.put(RISK_KEY_MOBILE[0],"2");
		nameValueMapping.put(RISK_KEY_MOBILE[1],"2");
		nameValueMapping.put(RISK_KEY_IDNUMBER[0],"3");
		nameValueMapping.put(RISK_KEY_CARDNUMBER[0],"4");
	}

	@Resource(name="rosterInnerApiService")
	private RosterInnerApiService  rosterInnerApiService;


	@Override
	public Map<String, Object> invokeRosterService(Map<String, Object> riskInput) {
		String sceneNo = String.valueOf(riskInput.get("scene")).substring(0,4);
		List<Rosters> list = this.findMappingInput(riskInput);
		Map<String,Object> resultMap = new HashMap<>();
		Map<String,Object> tempMap = null;
		if ( !list.isEmpty() ) {
			try {
				tempMap = rosterInnerApiService.innerVerifyHitRoster(sceneNo, list);
				if (!tempMap.isEmpty()) {
					resultMap.putAll(tempMap);
				}
			} catch (Exception e) {
				logger.error("Roster service happens error", e);
			}
		}
		return resultMap;

	}

	public List<Rosters> findMappingInput(Map<String, Object> riskInput){
		List<Rosters> verifyKeys = new ArrayList<>();
		Rosters roster = null;
		for (String[] allKey : ALL_KEYS) {
			for (String key : allKey) {
				String value = (String)riskInput.get(key);
				if ( value != null && !"".equals(value)  ){
					roster = new Rosters(key, nameValueMapping.get(key), value);
					verifyKeys.add(roster);
				}
			}
		}
		return verifyKeys;
	}
}
