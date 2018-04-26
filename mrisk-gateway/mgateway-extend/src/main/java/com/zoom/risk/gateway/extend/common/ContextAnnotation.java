package com.zoom.risk.gateway.extend.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jiangyulin
 *Sep 23, 2016
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ContextAnnotation {

    String value() default "";

    String[] includes();

    int order() default 999;
}
