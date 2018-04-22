package com.zoom.risk.platform.decision.service;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.decision.api.DTreeEngineApi;
import com.zoom.risk.platform.decision.api.DTreeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-config*.xml")
@ActiveProfiles("test")
public class DecisonTreeEngineTest {

    @Resource(name="dtreeEngineService")
    private DTreeEngineApi dtreeEngineService;


    @Test
    public void evaluate(){
        Map<String,Object> riskInput = new HashMap<>();
        riskInput.put("scene","090301");
        riskInput.put("idNumber","360729198705022111");
        riskInput.put("cardBindingMobile","13622766669");
        riskInput.put("mobile","13622766669");
        riskInput.put("cardNumber","6225881204227622");
        riskInput.put("userName","谭海军");

        DTreeResponse response = dtreeEngineService.evaluate(riskInput);
        System.out.println(JSON.toJSONString(response));
    }

}



