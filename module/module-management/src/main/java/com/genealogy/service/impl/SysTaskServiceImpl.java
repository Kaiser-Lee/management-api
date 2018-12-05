package com.genealogy.service.impl;

import com.genealogy.dao.SysTaskMapper;
import com.genealogy.po.SysTask;
import com.genealogy.service.SysTaskService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysTaskServiceImpl extends BaseServiceImpl<SysTask> implements SysTaskService {
    @Autowired
    private SysTaskMapper sysTaskMapper;
}