package com.management.common.config;

import com.management.brower.Constant;
import com.management.common.authorization.UserRealm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Value("${server.session-timeout}")
    private int tomcatTimeOut;

    @Value("${spring.cache.type}")
    private String cacheType;

    @Bean
    public ShiroFilterFactoryBean shiroFilter (SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager () {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());

        return securityManager;
    }

    @Bean
    public UserRealm userRealm () {
        return new UserRealm();
    }

    @Bean
    public SessionDAO sessionDAO () {
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {

        }
        return null;
    }

    @Bean
    public DefaultWebSessionManager sessionManager () {
        return null;
    }


    @Bean
    public LifecycleBeanPostProcessor getlifecycleBeanPostProcessor () {
        return new LifecycleBeanPostProcessor();
    }


}
