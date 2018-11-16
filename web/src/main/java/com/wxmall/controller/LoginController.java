package com.wxmall.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.management.redis.RedisManager;
import com.management.utils.IPUtils;
import com.management.xcontroller.BaseController;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping( value = "/userLogin", method = RequestMethod.GET)
    @ResponseBody
    public Object userLogin(String username, String password, HttpServletRequest request){
        try {
            String ip = IPUtils.getIpAddr(request);
            //password = MD5Utils.encrypt(username, password);
            RedisManager redisManager = RedisManager.getRedisSingleton();
            UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
            token.setRememberMe(false);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return "登录成功";
        } catch (AuthenticationException e) {
            logger.info(e.getMessage());
        }
        return "用户或密码错误";
    }

    /**
     * 安全退出
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpSession session){
        session.invalidate();
        return session.getId();
    }
}