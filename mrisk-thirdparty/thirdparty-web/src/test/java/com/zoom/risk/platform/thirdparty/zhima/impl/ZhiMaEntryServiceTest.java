package com.zoom.risk.platform.thirdparty.zhima.impl;

import com.alibaba.fastjson.JSONObject;
import com.zoom.risk.platform.thirdparty.tencent.service.ZhiMaEntryService;
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
public class ZhiMaEntryServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ZhiMaEntryServiceTest.class);

    @Resource(name="ZhiMaEntryService")
    private ZhiMaEntryService zhiMaEntryService;

    @Test
    public void invoke(){
        Map<String,Object> result = zhiMaEntryService.invoke("42212819790608281X","江玉林", 600);
        logger.info(JSONObject.toJSONString(result));
    }
}
