package com.zoom.risk.platform.thirdparty.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Invoker {

    String value() default "";

    String serviceName() default "";
}
