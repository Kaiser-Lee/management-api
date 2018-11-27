package com.genealogy.service.impl;

import com.genealogy.dao.UserMapper;
import com.genealogy.po.User;
import com.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list(Map<String, Object> map) {
        return userMapper.list(map);
    }
}