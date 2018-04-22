package com.zoom.risk.platform.thirdparty.bairong.impl;

import com.alibaba.fastjson.JSONObject;
import com.zoom.risk.platform.thirdparty.bairong.service.BaiRongEntryService;
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
public class BairongEntryServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(BairongEntryServiceTest.class);

    @Resource(name="baiRongEntryService")
    private BaiRongEntryService baiRongEntryService;

    @Test
    public void invoke(){
        Map<String,Object> result = baiRongEntryService.invoke("140502198811102244","王亮", "13986671110");
        logger.info("******\r\n*******\r\n*******\r\n" + JSONObject.toJSONString(result));
    }
}
