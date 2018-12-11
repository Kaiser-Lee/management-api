package com.iwhalecloud.retail.oms.handler;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.ztesoft.net.app.base.core.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import params.member.req.MemberQryReq;
import params.member.resp.MemberQryResp;
import zte.net.fjord.params.req.FJMemberLoginReq;
import zte.params.member.resp.MemberLoginResp;
import zte.params.order.req.OrderStatusEditReq;
import zte.params.order.resp.OrderStatusEditResp;

@Component
@Slf4j
public class OrderMangerHandler {




    @Autowired
    private ZteRopClient zteRopClient;

    public ResultVO<Member> login(String memberId,String userSessionId) {

        Member member= selectMember(memberId);
        //登录信息
        ResultVO<Member> resultVO = new ResultVO<>();
        FJMemberLoginReq loginReq = new FJMemberLoginReq();
        loginReq.setUserName(member.getUname());
        loginReq.setUserSessionId(userSessionId);
        MemberLoginResp memberLoginResp = zteRopClient.execute(loginReq, MemberLoginResp.class);
        log.info("gs_10010_builderOrder loginReq{},memberLoginResp{}", JSON.toJSONString(loginReq), JSON.toJSONString(memberLoginResp));
        if (!"0".equals(memberLoginResp.getError_code())) {
            resultVO.setResultMsg(memberLoginResp.getError_msg());
            resultVO.setResultCode(memberLoginResp.getError_code());
            return resultVO;
        }
        resultVO.setResultData(member);
        return resultVO;
    }


    public Member selectMember(String memberId) {
        MemberQryReq memberQryPageReq = new MemberQryReq();
        memberQryPageReq.setQry_con_type(MemberQryReq.QRY_CON_TYPE.ID);
        memberQryPageReq.setMember_id(memberId);
        MemberQryResp memberLoginResp = zteRopClient.execute(memberQryPageReq, MemberQryResp.class);
        return memberLoginResp.getMember();
    }


    /**
     * 订单状态 status
     */
    public final static String ORDER_STATUS_2 = "2"; //待支付
    public final static String ORDER_STATUS_4 = "4"; //待发货
    public final static String ORDER_STATUS_5 = "5"; //待收货确认
    public final static String ORDER_STATUS_6 = "6"; //完成
    public final static String ORDER_STATUS_7 = "7"; //收货确认（待评价）
    public final static String ORDER_STATUS_99_ = "-99"; //已删除

    public final static String ORDER_STATUS_10_ = "-10"; //取消完成

    //修改订单状态
    public CommonResultResp updateStatus(String orderId, String orderStatus){
        CommonResultResp resultVO=new CommonResultResp();
        OrderStatusEditReq statusEditReq = new OrderStatusEditReq();
        statusEditReq.setOrder_id(orderId);
        statusEditReq.setOrder_status(orderStatus);
        OrderStatusEditResp statusEditResp =zteRopClient.execute(statusEditReq,OrderStatusEditResp.class);
        log.info("gs_10010_updateStatus statusEditReq{},statusEditResp{}", JSON.toJSONString(statusEditReq), JSON.toJSONString(statusEditResp));
        resultVO.setResultCode(statusEditResp.getError_code());
        resultVO.setResultMsg(statusEditResp.getError_msg());
        return resultVO;
    }
}
