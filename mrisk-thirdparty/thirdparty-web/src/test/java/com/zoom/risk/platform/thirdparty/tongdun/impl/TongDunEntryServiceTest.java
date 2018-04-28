package com.zoom.risk.platform.thirdparty.tongdun.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.thirdparty.tongdun.service.TongDunEntryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations={"classpath*:spring/spring-config-*.xml"})
public class TongDunEntryServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(TongDunEntryServiceTest.class);

    @Resource(name="tongDunEntryService")
    private TongDunEntryService tongDunEntryService;

    @Test
    public void test(){
        Map<String,Object> resultMap = tongDunEntryService.invoke("131024198403102018","王永林","13582466569");
        System.out.println("***\r****\r*****\rresultMap = " + JSON.toJSONString(resultMap));
    }
}
