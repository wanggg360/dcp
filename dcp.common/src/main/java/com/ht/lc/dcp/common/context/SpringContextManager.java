package com.ht.lc.dcp.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-15 16:57
 * @Version 1.0
 **/

@Component
public class SpringContextManager implements ApplicationContextAware {

    private static SpringContextManager INSTANCE = new SpringContextManager();

    private static ApplicationContext applicationContext;

    public SpringContextManager() {
    }

    public static SpringContextManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
