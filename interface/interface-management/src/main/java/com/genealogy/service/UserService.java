package com.genealogy.service;

import com.genealogy.po.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> list (Map<String, Object> map);
}