package com.genealogy.service;

import com.genealogy.po.User;
import com.management.xservice.BaseService;

import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User> {

    List<User> list (Map<String, Object> map);
}