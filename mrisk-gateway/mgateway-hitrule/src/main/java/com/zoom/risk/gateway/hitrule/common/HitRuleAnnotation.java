package com.zoom.risk.gateway.hitrule.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jiangyulin
 *May 1, 2018
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface HitRuleAnnotation {

    String value() default "";

    String actionCode() default "";

}
