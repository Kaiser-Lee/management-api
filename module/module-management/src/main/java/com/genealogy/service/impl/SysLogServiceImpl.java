package com.genealogy.service.impl;

import com.genealogy.dao.SysLogMapper;
import com.genealogy.po.SysLog;
import com.genealogy.service.SysLogService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;
}