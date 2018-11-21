package com.wxmall.common.config;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.management.brower.Constant;
import com.wxmall.common.authorization.UserRealm;
import com.management.redis.BDSessionListeners;
import com.management.redis.RedisCacheManager;
import com.management.redis.RedisManager;
import com.management.redis.RedisSessionDAO;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class ShiroConfig {

    private static Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    @Value("${server.session-timeout}")
    private int tomcatTimeOut = 7200;

    @Value("${spring.cache.type}")
    private String cacheType = "ehcache";

    @Value("${spring.redis.host}")
    private String host = "122.114.110.171";

    @Value("${spring.redis.port}")
    private int port = 6379;

    @Value("${spring.redis.password}")
    private String password = "";

    @Value("${spring.redis.timeout}")
    private int timeout = 1000;

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
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)){
            securityManager.setCacheManager(cacheManager());
        }else{
            securityManager.setCacheManager(ehCacheManager());
        }
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public UserRealm userRealm () {
        return new UserRealm();
    }

    @Bean
    public SessionDAO sessionDAO () {
        log.info("是否使用redis缓存！");
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            log.info("RedisSessionDAO :" + cacheType);
            return new RedisSessionDAO();
        }
        return new MemorySessionDAO();
    }

    @Bean
    public LifecycleBeanPostProcessor getlifecycleBeanPostProcessor () {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setExpire(1800);
        log.info("配置redis连接设置###"+ host + ":" + port);
        return redisManager;
    }

    /**
     * CacheManager 实现redis缓存
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager cacheManager (){
        log.info("CacheManager 实现redis缓存");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public EhCacheManager ehCacheManager(){
        log.info("ehCacheManager ");
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(CacheManager.create());
        return ehCacheManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        log.info("开启shiro aop注解支持.");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        log.info("RedisSessionDAO shiro sessionDao层的实现 通过redis");
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        log.info("sessionManager 初始化");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(tomcatTimeOut*1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionDAO(sessionDAO());
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new BDSessionListeners());
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

    @Bean
    SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            manager.setCacheManager(ehCacheManager());
            log.info("设置getEhCacheManager");
        }
        log.info("设置sessionManager");
        manager.setSessionManager(sessionManager());
        return manager;
    }

}
