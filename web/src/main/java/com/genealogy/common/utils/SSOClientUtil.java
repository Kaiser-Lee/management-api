package com.genealogy.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ Author     ：lufangpu
 * @ Date       ：Created in 14:26 2018/12/4
 * @ Description：${description}
 * @ Modified By：
 * @Version: 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "ssoUrl")
public class SSOClientUtil {

    private static String serversUrlPrefix;

    private static String clientHostUrl;

    /**
     * 当客户端请求被拦截,跳往统一认证中心,需要带redirectUrl的参数,统一认证中心登录后回调的地址
     * 通过Request获取这次请求的地址 http://127.0.0.1:8086/*
     * @param request
     * @return
     */
    public static String getRedirectUrl(HttpServletRequest request){
        // 获取请求地址
        return clientHostUrl + request.getServletPath();
    }

    /**
     * 根据request获取跳转到统一认证中心的地址 http://122.114.110.171:8086//checkLogin?redirectUrl=http://122.114.110.171:8088/main
     * 通过Response跳转到指定的地址
     * @param request
     * @param response
     */
    public static void redirectToSSOURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = getRedirectUrl(request);
        StringBuilder url = new StringBuilder(50)
                .append(serversUrlPrefix)
                .append("/checkLogin?redirectUrl=")
                .append(redirectUrl);
        response.sendRedirect(url.toString());
    }

    /**
     * 获取Client完整登出地址
     * @return
     */
    public static String getClientLogOutUrl(){
        return clientHostUrl + "/logOut";
    }

    /**
     * 获取server完整登出地址
     * @return
     */
    public static String getServerLogOutUrl(){
        return serversUrlPrefix + "/logOut";
    }


    public static String getServersUrlPrefix() {
        return serversUrlPrefix;
    }

    public static void setServersUrlPrefix(String serversUrlPrefix) {
        SSOClientUtil.serversUrlPrefix = serversUrlPrefix;
    }

    public static String getClientHostUrl() {
        return clientHostUrl;
    }

    public static void setClientHostUrl(String clientHostUrl) {
        SSOClientUtil.clientHostUrl = clientHostUrl;
    }
}
