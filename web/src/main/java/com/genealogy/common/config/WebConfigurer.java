package com.genealogy.common.config;

import com.genealogy.common.filter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

   /* @Autowired
    private LoginInterceptor loginInterceptor;*/

    @Autowired
    private FileConfig fileConfig;

    final String[] notLoginInterceptPaths = {"/login/**","/index/**","/register/**"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileConfig.getShowPath() + "**").addResourceLocations("file:///" + fileConfig.getUploadPath());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
    }


}
