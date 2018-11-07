package com.wxmall.dao;

import com.management.xdao.IMybatisDao;
import com.wxmall.po.SellerUser;
import com.wxmall.po.SellerUser;

import java.util.List;
import java.util.Map;

public interface SellerUserMapper extends IMybatisDao<SellerUser> {
    int deleteByPrimaryKey(Long id);

    int insert(SellerUser record);

    int insertSelective(SellerUser record);

    SellerUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SellerUser record);

    int updateByPrimaryKey(SellerUser record);

    List<SellerUser> list(Map<String, Object> map);
}