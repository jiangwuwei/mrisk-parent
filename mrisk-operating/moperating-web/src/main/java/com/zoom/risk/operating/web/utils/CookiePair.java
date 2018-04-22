package com.zoom.risk.operating.web.utils;

/**
 * Created by jiangyulin on 2017/6/16.
 */
public class CookiePair {
    private String key;
    private String value;

    public CookiePair(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
