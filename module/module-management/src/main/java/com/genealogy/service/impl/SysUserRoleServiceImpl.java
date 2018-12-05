package com.genealogy.service.impl;

import com.genealogy.dao.SysUserRoleMapper;
import com.genealogy.po.SysUserRole;
import com.genealogy.service.SysUserRoleService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
}