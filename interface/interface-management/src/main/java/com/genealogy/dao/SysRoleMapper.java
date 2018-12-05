package com.genealogy.dao;

import com.genealogy.po.SysRole;
import com.management.xdao.IMybatisDao;

public interface SysRoleMapper extends IMybatisDao<SysRole> {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}