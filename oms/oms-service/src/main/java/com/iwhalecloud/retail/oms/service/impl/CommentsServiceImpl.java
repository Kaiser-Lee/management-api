package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;
import com.iwhalecloud.retail.oms.handler.OrderMangerHandler;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.CommentsService;
import com.iwhalecloud.retail.oms.service.GoodsEvaluateTotalService;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.order.UpdateOrderStatusRequestDTO;
import com.iwhalecloud.retail.order.service.OrderManagerOpenService;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Comments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import params.member.req.CommentAddReq;
import zte.params.member.req.CommentPageListReq;
import zte.params.member.resp.CommentAddResp;
import zte.params.member.resp.CommentPageListResp;

import java.util.List;

/**
 * @Author pjq
 * @Date 2018/10/10
 **/
@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private ZteRopClient client;
    @Autowired
    GoodsEvaluateTotalService goodsEvaluateTotalService;
    @Reference
    private OrderManagerOpenService orderManagerOpenService;

    @Autowired
    private OrderMangerHandler orderMangerHandler;

    @Override
    public ResultVO<Boolean> addComments(List<CommentAddReq> reqList) {
        log.info("CommentsServiceImpl addComments reqList={} ",reqList);
        for(CommentAddReq req:reqList){
            Member member = orderMangerHandler.selectMember(req.getMember_id());
            if(null == member){
                log.info("CommentsController addComments member={} ",member);
                ResultVO.error("member is null");
            }
            Comments comments = req.getComment();
            comments.setAuthor_id(req.getMember_id());
            comments.setAuthor(member.getUname());
            comments.setContact(member.getMobile());
            String orderId = comments.getOrder_id();
            String objectId = comments.getObject_id();
            CommentAddResp resp = null;
            try {
                resp = client.execute(req,CommentAddResp.class);
                if(null == resp){
                    return ResultVO.error("resp is null");
                }
                if(ResultCodeEnum.ERROR.getCode().equals(resp.getError_code())){
                    return ResultVO.error(resp.getError_msg());
                }
                //添加标签，给大数据处理
                TGoodsEvaluateTotalDTO totalDTO = new TGoodsEvaluateTotalDTO();
                totalDTO.setGoodsId(objectId);
                totalDTO.setEvaluateText(comments.getQuotas());
                goodsEvaluateTotalService.modifyGoodsEvaluate(totalDTO);

                //添加评论以后修改订单的状态

                UpdateOrderStatusRequestDTO request = new UpdateOrderStatusRequestDTO();
                request.setOrderId(orderId);
                request.setFlowType(ActionFlowType.ORDER_HANDLER_PJ.getCode());
                CommonResultResp comResp = orderManagerOpenService.updateOrderStatus(request);
                if(null == resp){
                    return  ResultVO.error("comResp is null");
                }
                log.info("CommentsServiceImpl addComments result={} ",comResp.getResultCode());

            }catch (Exception e){
                log.error("CommentsServiceImpl addComments Exception={} ",e);
                ResultVO.error();

            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<Page> queryCommoentForPage(CommentPageListReq req) {
        log.info("CommentsServiceImpl addComments req={} ",req);
        CommentPageListResp resp = null;
        try {
            resp = client.execute(req,CommentPageListResp.class);
            if(null == resp){
                return ResultVO.error("resp is null");
            }
            if(ResultCodeEnum.ERROR.getCode().equals(resp.getError_code())){
                return ResultVO.error(resp.getError_msg());
            }
        }catch (Exception e){
            log.error("CommentsServiceImpl queryCommoentForPage Exception={} ",e);
            return ResultVO.error();
        }
        if(null == resp){
            return ResultVO.error();
        }
        return ResultVO.success(resp.getPage());
    }
}
