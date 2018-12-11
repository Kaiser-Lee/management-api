package com.iwhalecloud.retail.oms.web.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import utils.MD5Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.consts.UserType;
import com.iwhalecloud.retail.oms.web.annotation.PassToken;
import com.iwhalecloud.retail.oms.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.oms.web.exception.UserNotLoginException;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        //每次请求清除线程变量中的用户信息
        MemberContext.removeMemberSession();
        UserContext.removeUserSession();

        String memberId = httpServletRequest.getHeader("memberId");
        if(StringUtils.isNotEmpty(memberId)){
            MemberContext.setMemberId(memberId);
            MemberContext.setUserSessionId(httpServletRequest.getHeader("sessionId"));
            return true;
        }
        // 从 session中获取，自动登录的时候会重置session中的 token
        String token = (String)httpServletRequest.getSession().getAttribute(OmsConst.SESSION_TOKEN);
        log.info("session token = {}",token);
        log.info("----登录校验sessionId-->  " + httpServletRequest.getSession().getId());
        // 如果http请求头没有token则尝试从session中获取
        if (StringUtils.isEmpty(token)) {
        	token = httpServletRequest.getHeader("token");
        	log.info("header token = {}",token);
        }

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
        	log.info("校验token = {}",token);
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null || token.equals("")) {
                    throw new UserNotLoginException("token为空，无效token，请重新登录");
                }

                String id = "";
                String sessionId = "";
                String type = "";
                try {
                    Map<String, Claim> claims = JWT.decode(token).getClaims();
                    id = claims.get("id").asString();
                    sessionId = claims.get("sessionId").asString();
                    type = claims.get("type").asString();
                    int exp = claims.get("exp").asInt();
                    int iat = claims.get("iat").asInt();
                    String selfToken = claims.get("selfToken").asString();
                    if(!selfToken.equals(MD5Util.compute("id="+id+"&sessionId="+sessionId+"&secret="+SECRET+"&exp="+String.valueOf(exp)+"&iat="+String.valueOf(iat)))){
                        throw new UserNotLoginException("校验不通过，无效token，请重新登录");
                    }
                } catch (JWTDecodeException j) {
                    throw new UserNotLoginException("token异常，请重新登录");
                }

                if (id == null) {
                    throw new UserNotLoginException("非法数据，请重新登录");
                }

                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                	e.printStackTrace();
                    throw new UserNotLoginException("token失效，请重新登录");
                }

                //将id放入线程变量
                if(type.equals(UserType.MEMBER.toString())){

                    MemberContext.setMemberId(id);
                    MemberContext.setUserSessionId(sessionId);
                }else if(type.equals(UserType.USER.toString())){
                    AdminUser adminUser = (AdminUser) httpServletRequest.getSession().getAttribute(OmsConst.SESSION_USER);
                    PartnerShopDTO shop = (PartnerShopDTO) httpServletRequest.getSession().getAttribute(OmsConst.SESSION_SHOP);

                    UserContext.setUserId(id);
                    UserContext.setSessionId(sessionId);
                    if(adminUser != null){
                        // 保存用户信息
                        UserContext.setAdminUser(adminUser);
                    }
                    if(shop != null){
                        // 保存店铺信息
                        UserContext.setPartnerShop(shop);
                    }
                }

                return true;
            }
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
