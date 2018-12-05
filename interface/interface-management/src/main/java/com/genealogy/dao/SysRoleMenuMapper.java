package com.genealogy.dao;

import com.genealogy.po.SysRoleMenu;
import com.management.xdao.IMybatisDao;

public interface SysRoleMenuMapper extends IMybatisDao<SysRoleMenu> {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);
}