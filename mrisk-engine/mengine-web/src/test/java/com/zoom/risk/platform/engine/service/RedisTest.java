/**
 * 
 */
package com.zoom.risk.platform.engine.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author jiangyulin
 *Nov 25, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-config*.xml")
@ActiveProfiles("test")
public class RedisTest {
	/*
	@Resource(name="redisClient")
	private RedisClient redisClient;
	
	@Resource(name="enginePolicyMapper")
	private EnginePolicyMapper enginePolicyMapper;
	
	
	@Test
	public void test(){
		List<Rule> listRules = enginePolicyMapper.findRule();
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(listRules);
		System.out.println("json input str [" + json + "]");
		redisClient.set("listRules", json);
		redisClient.expire("listRules", 10);
		json = redisClient.getString("listRules");
		listRules = gson.fromJson(json, new TypeToken<List<Rule>>(){}.getType());
		json = gson.toJson(listRules);
		System.out.println("json input str [" + json + "]");
	}*/
}
