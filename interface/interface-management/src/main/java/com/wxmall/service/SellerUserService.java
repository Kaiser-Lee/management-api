package com.wxmall.service;

import com.management.xservice.BaseService;
import com.wxmall.po.SellerUser;

import java.util.List;
import java.util.Map;

public interface SellerUserService extends BaseService<SellerUser> {
    List<SellerUser> list(Map<String, Object> map);
}