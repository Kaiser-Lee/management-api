package com.genealogy.dao;

import com.genealogy.po.SysDict;
import com.management.xdao.IMybatisDao;

public interface SysDictMapper extends IMybatisDao<SysDict> {
    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
}