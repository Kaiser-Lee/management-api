package com.management.controller;

import com.management.service.UserService;
import com.management.utils.IPUtils;
import com.management.utils.MD5Utils;
import com.management.xcontroller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/xweb/api/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(){
        Map<String, Object> map = new HashMap<String, Object>();
        return userService.list(map);
    }

    @RequestMapping( value = "/userLogin", method = RequestMethod.GET)
    @ResponseBody
    public Object userLogin(String username, String password, HttpServletRequest request){
        String ip = IPUtils.getIpAddr(request);
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "登录成功";
        } catch (AuthenticationException e) {
            return "用户或密码错误";
        }
    }
}