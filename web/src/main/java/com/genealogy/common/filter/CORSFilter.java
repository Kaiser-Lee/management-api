package com.genealogy.common.filter;

import com.genealogy.common.utils.SSOClientUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        // 判断是否有局部回话
        Boolean bool = (Boolean) session.getAttribute("isLogin");
        if(bool != null && bool){
            // 存在会话
            filterChain.doFilter(request, response);
            return;
        }

        // 没有局部会话 重定向到认证中心，检查是否有其他系统登录过
        SSOClientUtil.redirectToSSOURL(request, response);
       /* response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials", "true");// 是否支持Cookie跨域
        filterChain.doFilter(servletRequest, servletResponse);*/
    }

    @Override
    public void destroy() {

    }
}
