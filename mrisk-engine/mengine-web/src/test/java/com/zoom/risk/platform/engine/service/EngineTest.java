/*
 * Copyright (c) 2015-2020 LEJR.COM All Right Reserved
 */

package com.zoom.risk.platform.engine.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zoom.risk.platform.engine.api.DecisionResponse;
import com.zoom.risk.platform.engine.api.RuleEngineApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jiangyulin
 *Oct 17, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-config*.xml")
@ActiveProfiles("test")
public class EngineTest {

	@Resource(name="ruleEngineService")
	private RuleEngineApi ruleEngine;
			
	@Test
	public void evaluate(){
		Map<String,Object> riskMap = new HashMap<String,Object>();
		riskMap.put("uid", "13522122164");
		riskMap.put("scene", "010501");
		riskMap.put("payAmount", "50001");
		riskMap.put("riskId", "20160824153353812e9882a3979414b8f82981c76f626cce2");
		
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(new PolicyTargetStrategy())
			//.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
			//.serializeNulls()
			.create();
		
		for(int i =0 ; i < 10; i++ ){
			DecisionResponse enginePolicy = ruleEngine.evaluate(riskMap);
			System.out.println( gson.toJson(enginePolicy) );
		}
	}
}

