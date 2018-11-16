package com.management.utils;

import com.wxmall.po.SellerUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.List;

public class ShiroUtils {

    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubject () {
        return SecurityUtils.getSubject();
    }

    public static SellerUser getUser() {
        Object object = getSubject().getPrincipal();
        return (SellerUser)object;
    }

    public static Long getUserId () {
        return getUser().getId();
    }

    public static void logout () {
        getSubject().logout();
    }

    public static List<Principal> principalList () {
        List<Principal> list = null;
        return list;
    }
}
