package com.genealogy.controller;

import com.genealogy.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/user")
@Api(value = "系统用户接口")
public class UserController {

    @Resource
    private UserService userService;


}
