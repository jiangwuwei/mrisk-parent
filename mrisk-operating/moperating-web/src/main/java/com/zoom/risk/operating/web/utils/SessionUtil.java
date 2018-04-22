package com.zoom.risk.operating.web.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Created by liyi8 on 2017/6/9.
 */
public class SessionUtil {

    private static Logger logger = LogManager.getLogger(SessionUtil.class);

    public static final String SESSION_ID = "LE_SESSION_ID";
    public static final String LOGIN_ACCOUNT = "LE_LOGIN_ACCOUNT";


    public static synchronized String getSessionId() {
        Random random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        try {
            return Md5Utils.encrypt16(bytes);
        }catch (Exception e){
            return UUID.randomUUID().toString();
        }
    }

    public static String getFromCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0) {
            String sessionId = null;
            for(int i = 0; i < cookies.length; ++i) {
                Cookie cookie = cookies[i];
                if(key.equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
            return sessionId;
        }
        return null;
    }

    public static void addCookies(HttpServletResponse response, CookiePair... pairs){
        for( int i =0 ; i < pairs.length; i++ ) {
            CookiePair pair = pairs[i];
            Cookie cookie = new Cookie(pair.getKey(), pair.getValue());
            cookie.setPath("/");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
        }
    }

    public static void invalidateCookies(HttpServletResponse response, String... keys){
        for( int i =0 ; i < keys.length; i++ ) {
            Cookie cookie = new Cookie(keys[i], "empty");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
