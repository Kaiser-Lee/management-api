package com.genealogy.controller;

import com.genealogy.po.User;
import com.genealogy.service.UserService;
import com.management.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @desc 系统用户接口
 * @author lufangpu
 * @date 2018-11-29
 * @since 1.0.0
 *
 */
@Controller
@RequestMapping("/api/user")
@Api(description = "系统用户接口")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改用户信息")
    public R modifyUserInfo(@RequestBody User user){
        logger.info("修改用户信息");
        return R.ok();
    }

}
