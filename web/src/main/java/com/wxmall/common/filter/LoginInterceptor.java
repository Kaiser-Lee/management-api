package com.wxmall.common.filter;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.management.redis.RedisManager;
import com.wxmall.common.config.ApplicationContextRegister;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class LoginInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try{
            logger.info("登录拦截验证！");
            Subject subject = SecurityUtils.getSubject();
            Serializable token = subject.getSession().getId();
            RedisManager redisManager = ApplicationContextRegister.getBean(RedisManager.class);
            byte[] userId = redisManager.get(("sys:login:user_token_" + token).getBytes());
            if(userId != null){
                String tokenPre = redisManager.get(("sys:user:id_"+ userId).getBytes()).toString();
                if(!token.equals(tokenPre)){
                    // 进行重定向
                    return false;
                }else {
                    int expire = redisManager.getExpire();
                    if(expire < 1 * 60 * 30){
                        logger.info("设置过期时间为1分钟");
                        redisManager.setExpire(60 * 30);
                    }
                }

            }else {
                // 重定向
                return true;
            }
        }catch (Exception e){
            logger.info("preHandle=" + e.getMessage());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
