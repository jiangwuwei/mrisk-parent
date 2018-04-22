package com.zoom.risk.platform.thirdparty.web.interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class VerifyInterceptor  extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String name = request.getHeader("simpleVerification");
        boolean result = false;
        if ( "simpleVerification".equals(name)){
            result = true;
        }else{
            Map<String,Object> out = new HashMap<>();
            out.put("responseCode",401);
            out.put("responseDesc","Illegal access!");
            response.setHeader("Content-Type","application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(out));
            result = false;
        }
        return result;
    }
}
