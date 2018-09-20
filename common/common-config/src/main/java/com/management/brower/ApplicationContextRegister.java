package com.management.brower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextRegister {

    private static Logger logger = LoggerFactory.getLogger(ApplicationContextRegister.class);

    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * 设置spring的上下文
     * @param applicationContext spring上下文
     * @throws BeansException
     */
    public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
        logger.debug("application register -->",applicationContext);
        APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 获取容器
     * @return
     */
    public ApplicationContext getApplicationContext(){
        return APPLICATION_CONTEXT;
    }

    public static <T> T getBean (Class<T> type) {
        return APPLICATION_CONTEXT.getBean(type);
    }
}
