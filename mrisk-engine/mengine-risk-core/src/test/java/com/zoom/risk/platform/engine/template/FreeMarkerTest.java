/**
 * 
 */
package com.zoom.risk.platform.engine.template;

import com.zoom.risk.platform.engine.service.FreeMarkerService;
import com.zoom.risk.platform.engine.service.impl.FreeMarkerServiceImpl;
import com.zoom.risk.platform.engine.uitls.FreeMarkerConfigBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
public class FreeMarkerTest {


	@Test
	public void normalcases() {
		Configuration config = new FreeMarkerConfigBuilder()
				.addTemplate("sql1", "select * from xxxxx where risk_id = '${riskId}' or risk_id = '${riskId}'")
				.addTemplate("sql2", "select * from xxxxx where uid = '${uid}' or risk_id = '${riskId}'")
				.create();

		
		Map<String, Object> riskMap = new HashMap<String, Object>();
		riskMap.put("scene", "010501");
		riskMap.put("payAmount", 50001.2);
		riskMap.put("riskId", "20150824153353812e9882a3979414b8f82981c76f626cce2");

		String result = null;
		StringWriter writer = new StringWriter();
		try{
			Template template = config.getTemplate("sql2","utf-8");
			template.process(riskMap, writer);
			result = writer.getBuffer().toString();
			System.out.println(result);
		}catch(Exception e) {
			e.printStackTrace();
		}

    }


	@Test
	public void classicCompatible() {
		Configuration config = new FreeMarkerConfigBuilder()
				.addTemplate("sql1", "select * from xxxxx where risk_id = '${riskId!}' or uid = '${uid!}'")
				.create();
		config.setClassicCompatible(true);
		FreeMarkerService impl = new FreeMarkerServiceImpl();
		impl.setConfig(config);
		
		Map<String, Object> riskMap = new HashMap<>();
		riskMap.put("uid", null);


		String result = impl.merge("sql1", riskMap);
		System.out.println(result);


	}


}
