package com.genealogy.common.filter;

import com.genealogy.common.utils.SSOClientUtil;
import com.management.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录拦截验证
 * @date 2018-11-21
 * @author 卢方谱
 */
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

        String token = request.getParameter("token");
        if(StringUtils.isNoneBlank(token)){
            // token不为空说明地址包含token，拥有令牌
            String httpUrl = SSOClientUtil.getServersUrlPrefix() + "/verify";
            Map<String, String> params = new HashMap<String, String>();
            params.put("token", token);
            try {
                String isVerify = HttpUtil.sendHttpRequest(httpUrl, params);
                if("true".equals(isVerify)){
                    // 如果返回字符串为true, 则说明token是由认证中心产生的
                    session.setAttribute("isLogin", true);
                    filterChain.doFilter(request, response);
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        // 没有局部会话 重定向到认证中心，检查是否有其他系统登录过
        SSOClientUtil.redirectToSSOURL(request, response);
    }

    @Override
    public void destroy() {

    }
}
