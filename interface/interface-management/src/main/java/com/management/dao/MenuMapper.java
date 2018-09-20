package com.management.dao;

import com.management.po.Menu;
import com.management.xdao.IMybatisDao;

import java.util.List;

public interface MenuMapper extends IMybatisDao<Menu> {
    int deleteByPrimaryKey(Long menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<String> listUserPerms (Long id);
}