package com.genealogy.service.impl;

import com.genealogy.dao.SysRoleMenuMapper;
import com.genealogy.po.SysRoleMenu;
import com.genealogy.service.SysRoleMenuService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
}