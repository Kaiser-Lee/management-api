package com.genealogy.service.impl;

import com.genealogy.dao.SysDictMapper;
import com.genealogy.po.SysDict;
import com.genealogy.service.SysDictService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDict> implements SysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;
}