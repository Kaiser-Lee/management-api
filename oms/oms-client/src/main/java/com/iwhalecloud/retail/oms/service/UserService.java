package com.iwhalecloud.retail.oms.service;

import com.ztesoft.net.app.base.core.model.Member;


public interface UserService {

    Member findMemberByOpenId(String openId);

    Member findMemberById(String memberId);
}
