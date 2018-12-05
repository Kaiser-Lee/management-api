package com.genealogy.controller;

import com.genealogy.service.SysUserService;
import com.management.xcontroller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
}