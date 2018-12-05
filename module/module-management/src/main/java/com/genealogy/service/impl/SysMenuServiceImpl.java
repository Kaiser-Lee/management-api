package com.genealogy.service.impl;

import com.genealogy.dao.SysMenuMapper;
import com.genealogy.po.SysMenu;
import com.genealogy.service.SysMenuService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
}