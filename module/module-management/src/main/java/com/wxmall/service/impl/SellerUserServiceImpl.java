package com.wxmall.service.impl;

import com.management.xdao.IMybatisDao;
import com.management.xservice.ximpl.BaseServiceImpl;
import com.wxmall.dao.SellerUserMapper;
import com.wxmall.po.SellerUser;
import com.wxmall.service.SellerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SellerUserServiceImpl extends BaseServiceImpl<SellerUser> implements SellerUserService {
    @Autowired
    private SellerUserMapper sellerUserMapper;

    @Override
    public List<SellerUser> list(Map<String, Object> map) {
        return sellerUserMapper.list(map);
    }
}