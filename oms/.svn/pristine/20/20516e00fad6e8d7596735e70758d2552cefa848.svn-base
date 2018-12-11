package com.iwhalecloud.retail.oms.web.utils;

import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.web.interceptor.MemberContext;
import com.iwhalecloud.retail.oms.web.interceptor.UserContext;
import com.iwhalecloud.retail.order.consts.order.LoginUserType;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.Page;
import org.springframework.util.StringUtils;

import static com.iwhalecloud.retail.oms.consts.OmsConst.*;

public class ResponseComUtil {

    public static ResponseComBody page(Integer pageNo, Page page) {
        ResponseComBody responseComBody = new ResponseComBody();
        if (page == null) {
            return responseComBody;
        }
        responseComBody.setPageSize(page.getPageSize());
        if (!StringUtils.isEmpty(pageNo)) {
            responseComBody.setCurrentPageNo(new Long(pageNo));
        }
        responseComBody.setStart(page.getStart());
        responseComBody.setData(page.getResult());
        responseComBody.setTotalPageCount(page.getTotalPageCount());
        responseComBody.setTotalCount(page.getTotalCount());
        return responseComBody;
    }

    public static void RespToResultVO(CommonResultResp resp, ResultVO result) {
        result.setResultCode(resp.getResultCode());
        result.setResultMsg(resp.getResultMsg());
        result.setResultData(resp.getResultData());
    }

    public static void orderRespToResultVO(com.iwhalecloud.retail.order.dto.base.CommonResultResp resp,
                                           com.iwhalecloud.retail.order.dto.ResultVO result) {
        result.setResultCode(resp.getResultCode());
        result.setResultMsg(resp.getResultMsg());
        result.setResultData(resp.getResultData());
    }

    public static void orderRespToResultVO(com.iwhalecloud.retail.order.dto.base.CommonResultResp resp,
                                          ResultVO result) {
        result.setResultCode(resp.getResultCode());
        result.setResultMsg(resp.getResultMsg());
        result.setResultData(resp.getResultData());
    }

    public static String getLoginUserType() {

        if (!StringUtils.isEmpty(MemberContext.getMemberId())) {
            return LoginUserType.LOGIN_USER_TYPE_M.getCode(); //会员登录
        }
        AdminUser user = UserContext.getAdminUser();
        if (user == null) {
            return "login error";
        }
        switch (user.getFounder()) {
            case CURR_FOUNDER4:
                return LoginUserType.LOGIN_USER_TYPE_S.getCode();  //供应商
            case CURR_FOUNDER3:  //店长
                return LoginUserType.LOGIN_USER_TYPE_M.getCode(); //运营人员
            case CURR_FOUNDER6://店员
                return LoginUserType.LOGIN_USER_TYPE_M.getCode(); //运营人员
            case CURR_FOUNDER1:
                return LoginUserType.LOGIN_USER_TYPE_SM.getCode(); //超级管理员
            default:
                return "login error";
        }
    }

}
