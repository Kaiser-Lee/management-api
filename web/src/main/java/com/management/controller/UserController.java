package com.management.controller;

import com.management.po.User;
import com.management.service.UserService;
import com.management.xcontroller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(){
        User user = userService.selectByPrimaryKey(141L);
        System.out.println(user);
        Map<String, Object> map = new HashMap<String, Object>();
        return userService.list(map);
    }
}