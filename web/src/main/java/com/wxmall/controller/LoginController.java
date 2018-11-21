package com.wxmall.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.management.redis.RedisManager;
import com.management.utils.IPUtils;
import com.management.utils.R;
import com.management.xcontroller.BaseController;
import com.wxmall.common.config.ApplicationContextRegister;
import com.wxmall.po.SellerUser;
import com.wxmall.service.SellerUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/login")
@Api(description = "用户登录接口")
public class LoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SellerUserService sellerUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping( value = "/userLogin", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "登录接口")
    public R userLogin(@ApiParam("账号") @RequestParam String username, @ApiParam("密码") @RequestParam String password, HttpServletRequest request){
        try {
            String ip = IPUtils.getIpAddr(request);
            //password = MD5Utils.encrypt(username, password);
            RedisManager redisManager = RedisManager.getRedisSingleton();
            UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
            token.setRememberMe(false);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            //获取用户
            Map<String, Object> map = new HashMap<>();
            map.put("phone",username);
            List<SellerUser> list = sellerUserService.list(map);
            // 设置session
            request.getSession().setAttribute("sellerUser",list.get(0));
            //token
            Serializable id = subject.getSession().getId();
            //将token放入redis
            RedisManager manager = ApplicationContextRegister.getBean(RedisManager.class);
            manager.set(("sys:login:user_token_" + id).getBytes(), list.get(0).getId().toString().getBytes() , 60*30);
            manager.set(("sys:user:id_" + list.get(0).getId()).getBytes(),id.toString().getBytes(), 60*30);
            manager.set(("sys:user:user_info_" + list.get(0).getId()).getBytes(), JSONObject.toJSONString(list.get(0)).toString().getBytes(), 60*30);

            return R.ok();
        } catch (AuthenticationException e) {
            logger.info(e.getMessage());
        }
        return R.error("用户名或密码错误！");
    }

    /**
     * 安全退出
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpSession session){
        session.removeAttribute("sellerUser");
        return session.getId();
    }
}