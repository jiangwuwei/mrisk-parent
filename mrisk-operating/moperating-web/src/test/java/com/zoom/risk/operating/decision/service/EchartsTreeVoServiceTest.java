package com.zoom.risk.operating.decision.service;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by liyi8 on 2017/5/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config*.xml")
@ActiveProfiles("test")
public class EchartsTreeVoServiceTest {

    @Resource(name = "echartsTreeVoService")
    private EchartsTreeVoService echartsTreeVoService;

    @Test
    public void testGetTree(){
        System.out.println(JSONObject.toJSONString(echartsTreeVoService.buildTree(2L)));
    }
}
