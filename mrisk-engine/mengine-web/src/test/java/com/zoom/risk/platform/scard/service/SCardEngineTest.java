package com.zoom.risk.platform.scard.service;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.decision.api.DTreeEngineApi;
import com.zoom.risk.platform.decision.api.DTreeResponse;
import com.zoom.risk.platform.scard.api.SCardEngineApi;
import com.zoom.risk.platform.scard.api.ScoreCardResponse;
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
public class SCardEngineTest {

    @Resource(name="scardEngineApi")
    private SCardEngineApi scardEngineApi;


    @Test
    public void evaluate(){
        Map<String,Object> riskInput = new HashMap<>();
        riskInput.put("scene","909101");
        riskInput.put("test1", 22);
        riskInput.put("test2",20);
        riskInput.put("test3",20);
        riskInput.put("test4",20);
        riskInput.put("test5",20);
        riskInput.put("test6",20);
        riskInput.put("extendChallengeNumber",20);

        ScoreCardResponse response = scardEngineApi.evaluate(riskInput);
        System.out.println(JSON.toJSONString(response));
    }

}



