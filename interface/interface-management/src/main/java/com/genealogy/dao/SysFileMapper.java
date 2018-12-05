package com.genealogy.dao;

import com.genealogy.po.SysFile;
import com.management.xdao.IMybatisDao;

public interface SysFileMapper extends IMybatisDao<SysFile> {
    int deleteByPrimaryKey(Long id);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);
}