package com.zoom.risk.gateway.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jiangyulin
 *Sep 23, 2015
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ContextExtendedAnnotation {

    String value() default "";

    String[] includes();

    int order() default 999;
}
