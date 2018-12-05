package com.genealogy.controller;

import com.genealogy.service.SysRoleService;
import com.management.xcontroller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
}