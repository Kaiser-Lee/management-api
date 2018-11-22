package com.genealogy.dao;

import com.genealogy.po.User;
import com.management.xdao.IMybatisDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


public interface UserMapper extends IMybatisDao<User> {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);

    List<User> list(Map<String, Object> map);
}