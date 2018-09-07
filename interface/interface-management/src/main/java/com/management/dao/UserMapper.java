package com.management.dao;

import com.management.po.User;
import com.management.xdao.IMybatisDao;

import java.util.List;
import java.util.Map;

public interface UserMapper extends IMybatisDao<User> {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> list(Map<String, Object> map);
}