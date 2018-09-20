package com.management.common.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(BrowerSecurityConfig.class);

    protected void configure (HttpSecurity http) throws Exception {
        http.formLogin()                         // 当定义需要登录的用户的时候 跳转到登录界面
                .and()
                .authorizeRequests()            // 定义那些url需要被保护 哪些不需要被保护
                .anyRequest()                   //任何请求登录之后都可以访问
                .authenticated();
    }
}
