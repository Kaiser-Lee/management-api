package com.genealogy.service.impl;

import com.genealogy.dao.SysDeptMapper;
import com.genealogy.po.SysDept;
import com.genealogy.service.SysDeptService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDept> implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
}