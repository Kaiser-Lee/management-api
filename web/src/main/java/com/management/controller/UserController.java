package com.management.controller;

import com.management.service.UserService;
import com.management.utils.IPUtils;
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
public class UserController{
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
        return "登录测试";
    }
}