package com.iwhalecloud.retail.oms.web.controller.cms;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.service.ContentVediolv2Service;

@RestController
@CrossOrigin
@RequestMapping("/api/contentVediolv2")
public class ContentVediolv2Controller {
	
	@Reference
    private ContentVediolv2Service contentVediolv2Service;

    
    
    
    
}