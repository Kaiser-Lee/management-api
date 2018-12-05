package com.genealogy.dao;

import com.genealogy.po.SysLog;
import com.management.xdao.IMybatisDao;

public interface SysLogMapper extends IMybatisDao<SysLog> {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}