package com.zoom.risk.platform.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by jiangyulin on 2017/3/1.
 */
@Service("springHelper")
public class SpringHelper implements ApplicationContextAware {
    private boolean onlyForOnce = true;
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static <T> T getBean(String name, Class<T> tClass){
        return  applicationContext.getBean(name, tClass);
    }

    @PostConstruct
    public void init(){
    }


    public void setApplicationContext(ApplicationContext applicationContext) {
        if ( onlyForOnce  ) {
            this.applicationContext = applicationContext;
            onlyForOnce = false;
        }
    }
}
