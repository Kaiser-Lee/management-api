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
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录验证拦截
 *
 */

public class LoginInterceptor extends  HandlerInterceptorAdapter  {

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try{
            logger.info("登录拦截验证！");
            String basePath = request.getContextPath();
            String path = request.getRequestURI();
            if(!notLoginIntercept(path, basePath)){
                return true;
            }
            Subject subject = SecurityUtils.getSubject();
            Serializable token = subject.getSession().getId();
            RedisManager redisManager = ApplicationContextRegister.getBean(RedisManager.class);
            byte[] userId = redisManager.get(("sys:login:user_token_" + token).getBytes());
            if(userId != null){
                String tokenPre = redisManager.get(("sys:user:id_"+ userId.toString()).getBytes()).toString();
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
                //logger.info("尚未登录,请重新登录！");
                //response.sendRedirect("尚未登录,请重新登录！");
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

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    public boolean notLoginIntercept(String path, String basePath){
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<String>();
        notLoginPaths.add("/login");
        notLoginPaths.add("/index");
        if(notLoginPaths.contains(path)){
            return true;
        }
        return false;
    }
}
