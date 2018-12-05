package com.genealogy.dao;

import com.genealogy.po.SysDept;
import com.management.xdao.IMybatisDao;

public interface SysDeptMapper extends IMybatisDao<SysDept> {
    int deleteByPrimaryKey(Long deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
}