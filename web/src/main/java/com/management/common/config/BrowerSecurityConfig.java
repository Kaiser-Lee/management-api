package com.management.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws  Exception {
        http.formLogin()                        //定义当需要用户登录时候，转到的登录界面
                .and()
                .authorizeRequests()
                .anyRequest()                //任何请求登录之后都可以访问
                .authenticated();
    }
}
