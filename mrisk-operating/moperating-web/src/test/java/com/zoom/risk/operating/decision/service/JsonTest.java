package com.zoom.risk.operating.decision.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2017/5/27.
 */

public class JsonTest {
    private List<Object> staticList = new ArrayList<>();

    public static void main(String[] args) {
        ItemStyle itemStyle = new ItemStyle();
        itemStyle.setSymbol("circle");
        itemStyle.setSymbolSize(10);
        Map<String,Object> labelMap = Maps.newHashMap();
        labelMap.put("show",true);
        labelMap.put("position","top");
        Map<String,Object> normalMap = Maps.newHashMap();
        normalMap.put("label",labelMap);
        normalMap.put("color","rgba(255, 255, 255, 0)");
        itemStyle.setMap(normalMap);
        System.out.println(JSONObject.toJSONString(itemStyle));
    }


    @Test
    public void testListToJsonString(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> childMap = new HashMap<>();
        childMap.put("value","4");
        list.add(childMap);
        Map<String,Object> map = new HashMap<>();
        map.put("children", list);
        System.out.println(JSONObject.toJSONString(map));
    }

    @Test
    public void gc(){
        for( int i =0 ; i < 200; i++ ){
            List<String> list = new ArrayList<>();
            for(int k = 0; k < 100000; k++ ){
                list.add(new String("This is a test for gc " + i ));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ( i / 3  == 0 ){
                staticList.add(list);
            }
        }
    }
}

class ItemStyle{
    private String symbol;
    private Integer symbolSize;
    private Map<String,Object> map;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}

class Normal{
    private String label;
    private String color;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}


