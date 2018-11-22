package com.management.utils;

import com.genealogy.po.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public class ShiroUtils {

    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubject () {
        return SecurityUtils.getSubject();
    }

    public static User getUser() {
        Object object = getSubject().getPrincipal();
        return (User)object;
    }

    public static Long getUserId () {
        return getUser().getId();
    }

    public static void logout () {
        getSubject().logout();
    }

    public static List<Principal> principalList () {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }
}
