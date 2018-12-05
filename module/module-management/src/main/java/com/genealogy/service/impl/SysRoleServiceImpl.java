package com.genealogy.service.impl;

import com.genealogy.dao.SysRoleMapper;
import com.genealogy.po.SysRole;
import com.genealogy.service.SysRoleService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
}