/*
 * Copyright (c) 2015-2020 LEJR.COM All Right Reserved
 */

package com.zoom.risk.platform.engine.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.zoom.risk.platform.engine.mode.EngineDatabase;
import com.zoom.risk.platform.engine.mode.EnginePolicy;
import com.zoom.risk.platform.engine.mode.EnginePolicySet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @author jiangyulin
 *Oct 17, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-config*.xml")
@ActiveProfiles("test")
public class PolicyTest {
	
	@Resource(name="policyService")
	private PolicyService policyService;
	
	@Resource(name="policyCacheService")
	private PolicyCacheService policyCacheService;
			
	@Test
	public void daoFind(){
		Gson gson = new GsonBuilder()
				.setExclusionStrategies(new PolicyTargetStrategy())
	            //.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
	            //.serializeNulls()
	            .create();
		try{
			EngineDatabase engineDatabase = policyService.findEnginePolicies();
			Map<String,EnginePolicySet> map = policyService.buildEngineDatabase(engineDatabase);
			System.out.println(gson.toJson(map));
			map.forEach( (key, value) -> System.out.println(key + "=" + gson.toJson(value)) );
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void policyRouter(){
		Gson gson = new GsonBuilder().setExclusionStrategies(new PolicyTargetStrategy()).create();

		Map<String,Object> riskMap = new HashMap<String,Object>();
		riskMap.put("uid", "741963");
		riskMap.put("scene", "010501");
		//填充业务信息
		riskMap.put("identityType", "IT01");
		riskMap.put("payAmount", 50020);

		String sceneNo = String.valueOf(riskMap.get("scene")).substring(0,4);
		EnginePolicy enginePolicy = policyCacheService.getPolicy(sceneNo, riskMap).getEnginePolicy();
		System.out.println(gson.toJson(enginePolicy));
		
		riskMap.put("payAmount", 200);
		enginePolicy = policyCacheService.getPolicy(sceneNo, riskMap).getEnginePolicy();
		System.out.println(gson.toJson(enginePolicy));
		
		riskMap.put("payAmount", "50020.00");

		sceneNo = String.valueOf(riskMap.get("scene")).substring(0,4);
		enginePolicy = policyCacheService.getPolicy(sceneNo, riskMap).getEnginePolicy();
		System.out.println(gson.toJson(enginePolicy));
		
		riskMap.put("payAmount", "200.00");
		enginePolicy = policyCacheService.getPolicy(sceneNo, riskMap).getEnginePolicy();
		System.out.println(gson.toJson(enginePolicy));
		
	}
}

