package com.genealogy.service.impl;

import com.genealogy.dao.SysFileMapper;
import com.genealogy.po.SysFile;
import com.genealogy.service.SysFileService;
import com.management.xservice.ximpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile> implements SysFileService {
    @Autowired
    private SysFileMapper sysFileMapper;
}