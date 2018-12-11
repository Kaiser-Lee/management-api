package com.iwhalecloud.retail.oms.web.interceptor;

import java.io.Serializable;

public class MemberContext implements Serializable {

    private static ThreadLocal<String> memberIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> userSessionIdThreadLocal = new ThreadLocal<>();

    public static String getMemberId() {
        return memberIdThreadLocal.get();
    }

    public static void setMemberId(String memberId) {
        memberIdThreadLocal.set(memberId);
    }

    public static String getUserSessionId(){
        return userSessionIdThreadLocal.get();
    }

    public static void setUserSessionId(String userSessionId){
        userSessionIdThreadLocal.set(userSessionId);
    }

    public static void removeMemberSession() {
        memberIdThreadLocal.remove();
        userSessionIdThreadLocal.remove();
    }

}
