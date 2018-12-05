package com.genealogy.dao;

import com.genealogy.po.SysUser;
import com.management.xdao.IMybatisDao;

public interface SysUserMapper extends IMybatisDao<SysUser> {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}