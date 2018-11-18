package com.wxmall.controller;

import com.management.utils.IPUtils;
import com.management.utils.MD5Utils;
import com.management.xcontroller.BaseController;
import com.wxmall.service.SellerUserService;
import io.swagger.annotations.Api;
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
@RequestMapping("/api/sellerUser")
@Api(description = "账号管理接口")
public class SellerUserController extends BaseController {
    @Autowired
    private SellerUserService sellerUserService;

}