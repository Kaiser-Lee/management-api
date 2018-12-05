package com.genealogy.controller;

import com.genealogy.service.SysDeptService;
import com.management.xcontroller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;
}