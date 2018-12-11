package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.SystemService;
import com.ztesoft.net.eop.resource.model.AdminUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import params.adminuser.req.*;
import params.adminuser.resp.*;
import params.member.req.MemberListQryPageReq;
import params.member.req.MemberQryPageReq;
import params.member.resp.MemberQryResp;
import sun.security.krb5.internal.rcache.AuthList;
import zte.params.config.req.ConfigInfoReq;
import zte.params.config.resp.ConfigInfoResp;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    @Autowired
    private ZteRopClient client;

    @Override
    public AdminUserLoginResp adminUserLogin(AdminUserLoginReq req) {
        return null;
    }

    @Override
    public UserWdLoginResp userWdLogin(UserWdLoginReq req) {
        log.info("SystemServiceImpl.userWdLogin req={}", req);
        UserWdLoginResp resp;
        try {
            resp = client.execute(req,UserWdLoginResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.userWdLogin Rop Invoke Exception, ex={}", ex);
            throw ex;
        }

        return resp;
    }


    @Override
    public MemberQryResp qryMemberList(MemberQryPageReq req) {
        log.info("SystemServiceImpl.qryMemberList req={}", req);
        MemberQryResp resp;
        try {
            resp = client.execute(req, MemberQryResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.qryMemberList Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public MemberQryResp qryMemberListPage(MemberListQryPageReq req) {
        MemberQryResp resp = client.execute(req, MemberQryResp.class);
        return resp;
    }

    @Override
    public AdminUserRolePageResp getAdminListWithRole(AdminUserRolePageReq req) {
        log.info("SystemServiceImpl.getAdminListWithRole req={}", req);
        AdminUserRolePageResp resp;
        try {
            resp = client.execute(req, AdminUserRolePageResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.getAdminListWithRole Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public AdminAddResp addAdmin(AdminAddReq req) {
        log.info("SystemServiceImpl.addAdmin req={}", req);
        AdminAddResp resp;
        try {
            resp = client.execute(req, AdminAddResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.addAdmin Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public AdminEditUserResp editAdmin(AdminUserEditReq req) {
        // 根据userid获取数据库的用户数据 再设置要修改的字段
        log.info("SystemServiceImpl.editAdmin req={}", req);
        AdminEditUserResp resp;
        try {
            resp = client.execute(req, AdminEditUserResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.editAdmin Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public RoleListResp getAdminRoles(RoleReq req) {
        log.info("SystemServiceImpl.getAdminRoles req={}", req);
        RoleListResp resp;
        try {
            resp = client.execute(req, RoleListResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.getAdminRoles Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public UserPermissionResp editAdminRoles(UserPermissionReq req) {
        log.info("SystemServiceImpl.editAdminRoles req={}", req);
        UserPermissionResp resp;
        try {
            resp = client.execute(req, UserPermissionResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.editAdminRoles Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public AdminUser getAdminById(String userId) {
        log.info("SystemServiceImpl.getAdminById req={}", userId);
        ZbAdminUserGetReq req = new ZbAdminUserGetReq();
        req.setUserid(userId);
        ZbAdminUserGetResp resp;
        try {
            resp = client.execute(req, ZbAdminUserGetResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.getAdminById Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp.getAdminUser();
    }

    @Override
    public AdminUser getAdminByUserName(String userName) {
        log.info("SystemServiceImpl.getAdminById req={}", userName);
        AdminUserReq req = new AdminUserReq();
        // 只能设置username这个字段的值，其他的不能设置
        req.setUser_name(userName);
        AdminUserResp resp;
        try {
            resp = client.execute(req, AdminUserResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.getAdminById Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp.getAdminUser();
    }


    @Override
    public AdminUserDelResp deleteAdmin(AdminUserDelReq req) {
        log.info("SystemServiceImpl.deleteAdmin req={}", req);
        AdminUserDelResp resp;
        try {
            resp = client.execute(req, AdminUserDelResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.deleteAdmin Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public AdminSetStatusResp setAdminStatus(AdminSetStatusReq req) {
        log.info("SystemServiceImpl.setAdminStatus req={}", req);
        AdminSetStatusResp resp;
        try {
            resp = client.execute(req, AdminSetStatusResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.setAdminStatus Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public AdminUserDisableResp disableAdmin(AdminUserDisableReq req) {
        log.info("SystemServiceImpl.disableAdmin req={}", req);
        AdminUserDisableResp resp;
        try {
            resp = client.execute(req, AdminUserDisableResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.disableAdmin Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public RolePageResp getAllRoles(RolePageReq req) {
        log.info("SystemServiceImpl.getAllRoles req={}", req);
        RolePageResp resp;
        try {
            resp = client.execute(req, RolePageResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.getAllRoles Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public RoleListResp getAllRoleList(RoleListReq req) {
        return client.execute(req, RoleListResp.class);
    }

    @Override
    public AuthListResp getAllAuths(AuthPageReq req) {
        log.info("SystemServiceImpl.getAllAuths req={}", req);
        AuthListResp resp;
        try {
            resp = client.execute(req, AuthListResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.getAllAuths Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public AuthResp getAuthsByRoleId(AuthReq req) {
        log.info("SystemServiceImpl.getAuthByRoleId req={}", req);
        AuthResp resp;
        try {
            resp = client.execute(req, AuthResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.getAuthByRoleId Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public RoleAddResp addRole(RoleAddReq req) {
        log.info("SystemServiceImpl.addRole req={}", req);
        RoleAddResp resp;
        try {
            resp = client.execute(req, RoleAddResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.addRole Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public RoleAddResp editRole(RoleEditReq req) {
        log.info("SystemServiceImpl.editRole req={}", req);
        RoleAddResp resp;
        try {
            resp = client.execute(req, RoleAddResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.editRole Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public RoleDelResp deleteRole(RoleDelReq req) {
        log.info("SystemServiceImpl.deleteRole req={}", req);
        RoleDelResp resp;
        try {
            resp = client.execute(req, RoleDelResp.class);
        } catch(Exception ex) {
            log.error("SystemServiceImpl.deleteRole Rop Invoke Exception, ex={}", ex);
            throw ex;
        }
        return resp;
    }

    @Override
    public ConfigInfoResp getConfigInfoById(String id) {
        ConfigInfoReq req = new ConfigInfoReq();
        req.setCf_id(id);
        return client.execute(req, ConfigInfoResp.class);
    }
}
