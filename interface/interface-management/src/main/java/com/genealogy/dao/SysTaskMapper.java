package com.genealogy.dao;

import com.genealogy.po.SysTask;
import com.management.xdao.IMybatisDao;

public interface SysTaskMapper extends IMybatisDao<SysTask> {
    int deleteByPrimaryKey(Long id);

    int insert(SysTask record);

    int insertSelective(SysTask record);

    SysTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysTask record);

    int updateByPrimaryKey(SysTask record);
}