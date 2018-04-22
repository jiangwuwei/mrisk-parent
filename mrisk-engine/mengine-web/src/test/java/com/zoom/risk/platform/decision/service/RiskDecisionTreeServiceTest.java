package com.zoom.risk.platform.decision.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zoom.risk.platform.decision.po.TRule;
import com.zoom.risk.platform.decision.proxy.RiskDTreeProxyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-config*.xml")
@ActiveProfiles("test")
public class RiskDecisionTreeServiceTest {
	private ApplicationContext applicationContext;
	
	@Resource(name="riskDecisionTreeProxyService")
	private RiskDTreeProxyService riskDecisionTreeProxyService;

	@Test
	public void mockDecisionTree2Rule(){
		List<TRule> list = riskDecisionTreeProxyService.mockDecisionTree2Rule(2L);
		list.forEach((rule)->{
			System.out.println(JSON.toJSONString(rule, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
		});
	}

}
