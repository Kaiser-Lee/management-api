package com.management.service.impl;

import com.management.dao.MenuMapper;
import com.management.po.Menu;
import com.management.service.MenuService;
import com.management.xdao.IMybatisDao;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    public IMybatisDao<Menu> getBaseDao() {
        return menuMapper;
    }

    @Override
    public List<String> listPerms(Long id) {
        return menuMapper.listUserPerms(id);
    }
}