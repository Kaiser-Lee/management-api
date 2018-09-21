package com.management.service;

import com.management.po.Menu;
import com.management.xservice.BaseService;

import java.util.List;

public interface MenuService extends BaseService<Menu> {

    List<String> listPerms (Long id);
}