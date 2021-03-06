package com.genealogy.common.authorization;

import com.genealogy.po.User;
import com.genealogy.service.UserService;
import com.management.utils.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 为当前subject授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Long userId = ShiroUtils.getUserId();
        Map<String, Object> params = new HashMap<String, Object>();
        //MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
       // PrincipalCollection perms = (PrincipalCollection) menuService.listPerms(userId);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        //info.setPrincipals(perms);
        //info.setStringPermissions(perms);
        return (AuthorizationInfo) info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number",username);
        //UserMapper userMapper = ApplicationContextRegister.getBean(UserMapper.class);
        //Object password = authenticationToken.getCredentials();
        String password = new String((char[])authenticationToken.getCredentials());
        List<User> list  = userService.list(params);
        User user = null;
        if (list.size()>0) {
            user = list.get(0);
        }
        // 账号密码不存在
        if(user == null) {
            throw new UnknownAccountException("账号或密码不存在！");
        }
        // 密码错误
        if(!password.equals(user.getPassword())){
            throw new UnknownAccountException("密码错误！");
        }
        // 账号锁定
        /*if(user.getStatus() == 0) {
            throw new UnknownAccountException("账号已被锁定，请联系管理员");
        }*/
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
