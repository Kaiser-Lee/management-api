package com.genealogy.service.impl;

import com.genealogy.dao.SysUserMapper;
import com.genealogy.po.SysUser;
import com.genealogy.service.SysUserService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

}