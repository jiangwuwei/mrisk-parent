package com.zoom.risk.operating.decision.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zoom.risk.operating.decision.po.TRule;
import com.zoom.risk.operating.decision.proxy.RiskDecisionTreeProxyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config*.xml")
@ActiveProfiles("test")
public class RiskDecisionTreeServiceTest implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	@Resource(name="riskDecisionTreeProxyService")
	private RiskDecisionTreeProxyService riskDecisionTreeProxyService;

	@Test
	public void mockDecisionTree2Rule(){
		List<TRule> list = riskDecisionTreeProxyService.mockDecisionTree2Rule(1L);
		list.forEach((rule)->{
			System.out.println(JSON.toJSONString(rule, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
		});
	}

	@Test
	public void buildDecisionTree() {
		//riskDecisionTreeProxyService.submitPolicyAndBuildDecisionTree(1L);
		System.out.println(JSONObject.toJSONString(riskDecisionTreeProxyService.buildDecisionTree(1L)));
	}

	@Test
	public void getNextNumber(){
		String value = riskDecisionTreeProxyService.getNextNumber("TQ","0908");
		System.out.println(value);

		value = riskDecisionTreeProxyService.getNextNumber("TN","0908");
		System.out.println(value);


		value = riskDecisionTreeProxyService.getNextNumber("TR","0908");
		System.out.println(value);

		value = riskDecisionTreeProxyService.getNextNumber("TT","0908");
		System.out.println(value);
	}



	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
