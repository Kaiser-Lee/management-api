package com.iwhalecloud.retail.oms.web.controller.rop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.service.TokenService;
import com.iwhalecloud.retail.oms.web.annotation.PassToken;
import com.iwhalecloud.retail.oms.web.controller.BaseController;

@RestController
@RequestMapping("/api/login")
public class LoginController extends BaseController  {

	@Reference
    private TokenService tokenService;

    @GetMapping(value="authentication")
    @PassToken
    public String getTockenByOpenId(String memberId, String sessionId){
        String token = tokenService.getMemberToken(memberId, sessionId);
        return token;
    }
}
