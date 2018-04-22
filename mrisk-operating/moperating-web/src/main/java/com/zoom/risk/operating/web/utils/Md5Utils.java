package com.zoom.risk.operating.web.utils;

import org.springframework.util.DigestUtils;

/**
 * Created by liyi8 on 2017/3/27.
 */
public class Md5Utils {

    public static String encrypt16(String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes()).substring(8, 24);
    }

    public static String encrypt16(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes).substring(8, 24);
    }
}
