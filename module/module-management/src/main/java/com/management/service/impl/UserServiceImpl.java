package com.management.service.impl;

import com.management.dao.UserMapper;
import com.management.po.User;
import com.management.service.UserService;
import com.management.xdao.IMybatisDao;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    public IMybatisDao<User> getBaseDao() {
        return userMapper;
    }
}