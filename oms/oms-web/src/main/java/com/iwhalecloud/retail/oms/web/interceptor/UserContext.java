package com.iwhalecloud.retail.oms.web.interceptor;

import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Slf4j
public class UserContext implements Serializable {

	private static final long serialVersionUID = -6634783591580517814L;

	private static ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> sessionIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<AdminUser> adminUserThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<PartnerShopDTO> shopThreadLocal = new ThreadLocal<>();

    public static String getUserId() {
        return userIdThreadLocal.get();
    }

    public static void setUserId(String userId) {
        userIdThreadLocal.set(userId);
    }

    public static String getSessionId() {
        return sessionIdThreadLocal.get();
    }

    public static void setSessionId(String sessionIdId) {
        sessionIdThreadLocal.set(sessionIdId);
    }

    public static void removeUserSession() {
        userIdThreadLocal.remove();
        sessionIdThreadLocal.remove();
        adminUserThreadLocal.remove();
        shopThreadLocal.remove();
    }
    /**
     * 获取当前servlet的request对象，不在servlet环境下返回null
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        HttpServletRequest request = attrs.getRequest();
        return request;
    }

    /**
     * 获取当前servlet的request对象，不在servlet环境下返回null.
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final HttpSession getSession() {
        HttpServletRequest req = getRequest();
        if (req == null) {
            return null;
        }
        HttpSession session = req.getSession();
        return session;
    }
    /**
     * 获取当前登录的用户信息,没有登录则返回null
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final AdminUser getAdminUser() {

        AdminUser adminUser = adminUserThreadLocal.get();
        //优先从线程变量取，取到就返回
        if(adminUser != null){
            return adminUser;
        }

        //从httpsession中获取
        HttpSession httpSession = getSession();
        if (httpSession != null) {
            adminUser = (AdminUser) httpSession.getAttribute(OmsConst.SESSION_USER);
            return adminUser;
        }

        return null;
    }

    /**
     * 设置登录信息到session中.
     *
     * @param adminUser 登录信息
     * @author Ji.kai 2018-11-5
     */
    public static final void setAdminUser(AdminUser adminUser) {
        HttpServletRequest req = getRequest();
        if (req == null) {
            log.warn("用户信息设置失败，adminUser为空！");
            return;
        }
        HttpSession session = req.getSession(true);
        //清空这些信息，减轻写入session redis缓存的内容，读取的时候在增加从redis中读取
        session.setAttribute(OmsConst.SESSION_USER, adminUser);
        if (adminUser != null)
            log.info("添加用户信息到session中，userId={}，userName={}",adminUser.getUserid(),adminUser.getUsername());

        //设置线程变量
        adminUserThreadLocal.set(adminUser);
    }


    /**
     * 获取当前登录的用户的店铺信息,没有登录则返回null
     *
     * @return
     * @author zwl 2018-11-5
     */
    public static final PartnerShopDTO getPartnerShop() {

        PartnerShopDTO partnerShopDTO = shopThreadLocal.get();
        //优先从线程变量取，取到就返回
        if(partnerShopDTO != null){
            return partnerShopDTO;
        }

        //从httpsession中获取
        HttpSession httpSession = getSession();
        if (httpSession != null) {
            partnerShopDTO = (PartnerShopDTO) httpSession.getAttribute(OmsConst.SESSION_SHOP);
            return partnerShopDTO;
        }

        return null;
    }

    /**
     * 设置店铺信息到session中.
     *
     * @param partnerShop 店铺信息
     * @author zwl 2018-11-15
     */
    public static final void setPartnerShop(PartnerShopDTO partnerShop) {
        HttpServletRequest req = getRequest();
        if (req == null) {
            log.warn("用户店铺信息设置失败！");
            return;
        }
        HttpSession session = req.getSession(true);
        //清空这些信息，减轻写入session redis缓存的内容，读取的时候在增加从redis中读取
        session.setAttribute(OmsConst.SESSION_SHOP, partnerShop);
        if (partnerShop != null)
            log.info("添加用户店铺信息到session中，shopId={}，shopName={}",partnerShop.getPartnerShopId(),partnerShop.getName());

        //设置线程变量
        shopThreadLocal.set(partnerShop);
    }




    /**
     * 获取当前登录用户的门店,没有登录则返回null
     *
     * @return
     * @author Ji.kai 2018-11-5
     */
    public static final String getRelcode() {

        AdminUser adminUser = adminUserThreadLocal.get();
        //优先从线程变量取，取到就返回
        if(adminUser != null){
            log.warn("用户信息获得失败，adminUser为空！");
            return adminUser.getRelcode();
        }

        //从httpsession中获取
        HttpSession httpSession = getSession();
        if (httpSession != null) {
            log.warn("用户信息获得失败，adminUser为空！");
            adminUser = (AdminUser) httpSession.getAttribute(OmsConst.SESSION_USER);
            return adminUser.getRelcode();
        }

        return null;
    }
    
    /**
     * 获取厅店ID，假如工号的founder不是3或者6，返回null
     * @return
     */
    public static final String getPartnerShopId() {
        AdminUser adminUser = getAdminUser();
        if (adminUser == null) {
        	log.warn("获取厅店ID失败，登录工号信息为空");
        	return null;
        }
        if (OmsConst.CURR_FOUNDER3 != adminUser.getFounder()
        		|| OmsConst.CURR_FOUNDER6 != adminUser.getFounder()) {
        	return null;
        	
        }
        return adminUser.getRelcode();
        
    }
    
    /**
     * 获取厅店ID，假如工号的founder不是3或者6，返回null
     * @return
     */
    public static final String getSupplierId() {
        AdminUser adminUser = getAdminUser();
        if (adminUser == null) {
        	log.warn("获取供货商ID失败，登录工号信息为空");
        	return null;
        }
        
        if (OmsConst.CURR_FOUNDER4 != adminUser.getFounder()) {
        	log.warn("获取供货商ID失败，,工号所属founder={}",adminUser.getFounder());
        	return null;
        }
        return adminUser.getRelcode();
    }
    
    /**
     * 是否分销商
     * @return
     */
    public static boolean isPartner() {
    	 AdminUser adminUser = getAdminUser();
         if (adminUser == null) {
         	log.warn("判断是否代理商失败，登录工号信息为空");
         	return false;
         }
         
         return OmsConst.CURR_FOUNDER3 == adminUser.getFounder() || OmsConst.CURR_FOUNDER6 == adminUser.getFounder();
    }
    
    /**
     * 是否供货商
     * @return
     */
    public static boolean isSupplier() {
    	 AdminUser adminUser = getAdminUser();
         if (adminUser == null) {
         	log.warn("判断是否供货商失败，登录工号信息为空");
         	return false;
         }
         
         return OmsConst.CURR_FOUNDER4 == adminUser.getFounder();
    }
}
