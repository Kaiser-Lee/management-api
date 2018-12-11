package com.iwhalecloud.retail.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import params.member.req.MemberByIdReq;
import params.member.resp.MemberByIdResp;
import zte.params.member.req.MemberLoginReq;
import zte.params.member.resp.MemberLoginResp;

import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.UserService;
import com.ztesoft.net.app.base.core.model.Member;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ZteRopClient client;

    @Override
    public Member findMemberByOpenId(String openId) {
        MemberLoginReq req = new MemberLoginReq();
        req.setOpenId(openId);
        MemberLoginResp resp = client.execute(req, MemberLoginResp.class);
        return resp.getMember();
    }

    @Override
    public Member findMemberById(String memberId) {
        MemberByIdReq req = new MemberByIdReq();
        req.setMember_id(memberId);
        MemberByIdResp resp = client.execute(req, MemberByIdResp.class);
        return resp.getMember();
    }
}
