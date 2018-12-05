package com.genealogy.dao;

import com.genealogy.po.SysMenu;
import com.management.xdao.IMybatisDao;

public interface SysMenuMapper extends IMybatisDao<SysMenu> {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
}