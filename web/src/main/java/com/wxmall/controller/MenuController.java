package com.wxmall.controller;

import com.management.service.MenuService;
import com.management.xcontroller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;
}