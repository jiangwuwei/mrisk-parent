package com.zoom.risk.platform.thirdparty.minivision.impl;


import com.zoom.risk.platform.thirdparty.minivision.service.MinivisionEntryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations={"classpath*:spring/spring-config-*.xml"})
public class MinivisionEntryServiceTest {

    @Resource(name="minivisionBlacklistService")
    private MinivisionEntryService minivisionBlacklistService;

    @Resource(name="minivisionCrimeService")
    private MinivisionEntryService minivisionCrimeService;

    @Test
    public void test(){
        Map<String,Object> map = minivisionBlacklistService.invoke("142702199702014226","张三","13986671110");
        System.out.println(" blacklist ***********\r\n***********\n***********\n" + map);

        map = minivisionCrimeService.invoke("142702199702014226","张三","13986671110");
        System.out.println("crime ***********\r\n***********\n***********\n" + map);
    }
}
