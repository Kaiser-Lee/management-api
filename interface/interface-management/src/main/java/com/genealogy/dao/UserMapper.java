package com.genealogy.dao;

import com.genealogy.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> list(Map<String, Object> map);
}