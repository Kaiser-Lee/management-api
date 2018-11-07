package com.wxmall.controller;

import com.management.utils.IPUtils;
import com.management.utils.MD5Utils;
import com.management.xcontroller.BaseController;
import com.wxmall.service.SellerUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SellerUserController extends BaseController {
   /* @Autowired
    private SellerUserService sellerUserService;*/

    @RequestMapping( value = "/userLogin", method = RequestMethod.GET)
    @ResponseBody
    public Object userLogin(String username, String password, HttpServletRequest request){
        String ip = IPUtils.getIpAddr(request);
        //password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
        token.setRememberMe(false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "登录成功";
        } catch (AuthenticationException e) {
            return "用户或密码错误";
        }
    }
}