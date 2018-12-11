package com.iwhalecloud.retail.oms.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.TokenService;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.app.base.core.model.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.alibaba.dubbo.config.annotation.Service;

import utils.MD5Util;
import zte.params.member.req.MemberLoginReq;
import zte.params.member.resp.MemberLoginResp;

import com.iwhalecloud.retail.oms.consts.UserType;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiresAt}")
    private String expiresAt;

    @Autowired
    private ZteRopClient client;

    @Override
    public String getMemberToken(String memberId, String memberSessionId) {
        return createToken(memberId, memberSessionId, UserType.MEMBER);
    }

    @Override
    public String getUserToken(String userId, String userSessionId) {
//        MemberLoginReq req = new MemberLoginReq();
//        req.setOpenId(userId);
//        MemberLoginResp resp = client.execute(req, MemberLoginResp.class);
//        if(resp.getMember() == null){
//            return "";
//        }
//        Member member = resp.getMember();
        if(StringUtil.isEmpty(userId)){
            return "";
        }
        return createToken(userId, userSessionId, UserType.USER);
    }

    private String createToken(String id, String sessionId, UserType type){
        Date iat = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, Integer.parseInt(expiresAt));
        Date exp = nowTime.getTime();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = "";
        try {
            token = JWT.create()
                    .withHeader(map)
                    .withClaim("id", id)
                    .withClaim("sessionId", sessionId)
                    .withClaim("type",type.toString())
                    .withClaim("selfToken", MD5Util.compute("id="+id+"&sessionId="+sessionId+"&secret="+SECRET+"&exp="+String.valueOf(exp.getTime()/1000)+"&iat="+String.valueOf(iat.getTime()/1000)))
                    .withExpiresAt(exp)
                    .withIssuedAt(iat)
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;
    }
}
