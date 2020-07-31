package com.alvin.boot.tinyid.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public final class SpringContextUtils implements ApplicationContextAware {
    /**
     * 应用上下文
     */
    private static ApplicationContext applicationContext;

    private SpringContextUtils() {
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (SpringContextUtils.applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanName, clazz);
    }
}
