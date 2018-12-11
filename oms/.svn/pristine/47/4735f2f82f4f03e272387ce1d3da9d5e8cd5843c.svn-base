package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.oms.dto.TestUserDTO;
import com.iwhalecloud.retail.oms.entity.UserCopyTest;
import com.iwhalecloud.retail.oms.manager.TestUserManger;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import zte.params.member.req.MemberLoginReq;
import zte.params.member.resp.MemberLoginResp;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    public String sayHello(String msg) {
        return "hello " + msg;
    }

    @Autowired
    private TestUserManger testUserManger;

    @Autowired
    private ZteRopClient client;


    @Override
    public String testMabatis() {
        List<TestUserDTO> list = testUserManger.testFindUser();
        log.info("service list{}", JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }

    @Override
    public String testH2Mapper() {
        List<UserCopyTest> list = testUserManger.testSelect();
        log.info("service list{}", JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }

    @Override
    public MemberLoginResp testRop() {
        MemberLoginReq req = new MemberLoginReq();
        req.setUserName("admin");
        req.setPwd("123");

        MemberLoginResp resp = client.execute(req, MemberLoginResp.class);

        log.info("testRop resp{}", JSON.toJSONString(resp));

        return resp;
    }

//    @Autowired
//    FileManagerServiceImpl fileManagerServiceImpl;

    public String upload() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    fileManagerServiceImpl.uploadImg();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        return "";
    }
}
