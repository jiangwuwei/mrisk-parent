package com.zoom.risk.operating.decision.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2017/6/7.
 */
public class TestNullObjectToInteger {

    @Test
    public void test(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("testKey", null);
        Integer testValue = (Integer)map.get("testKey");
        map.put("testInteger", 222);
        String xxx  = String.valueOf(map.get("testInteger"));
        String yyy  = String.valueOf(map.get("testKey"));
        Assert.assertEquals(null, map.get("testKey"));
    }

    @Test
    public void testListIndexOf(){
        List<String> list = new ArrayList();
        list.add("1");
        list.add("2");
        Assert.assertTrue(list.indexOf(String.valueOf(1)) >= 0);
    }

}
