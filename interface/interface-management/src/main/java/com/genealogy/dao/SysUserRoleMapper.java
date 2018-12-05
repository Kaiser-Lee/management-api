package com.genealogy.dao;

import com.genealogy.po.SysUserRole;
import com.management.xdao.IMybatisDao;

public interface SysUserRoleMapper extends IMybatisDao<SysUserRole> {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}