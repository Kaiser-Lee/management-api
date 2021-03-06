package com.iwhalecloud.retail.oms.web.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import params.cart.req.CartAddReq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.consts.GoodsRankingsType;
import com.iwhalecloud.retail.oms.dto.GoodsRankingsDTO;
import com.iwhalecloud.retail.oms.service.GoodsRankingsService;
import com.iwhalecloud.retail.oms.web.annotation.GoodsRankingsAnnotation;
import com.iwhalecloud.retail.order.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order.dto.ResultVO;
import com.iwhalecloud.retail.order.dto.resquest.order.UpdateOrderStatusRequestDTO;


/**
 * @Author he.sw
 * @Date 2018/11/07
 **/

@Slf4j
@Aspect
@Order(-99)
@Component
public class GoodsRankingsInterceptor {

	@Reference
    private GoodsRankingsService goodsRankingsService;
	
	private static final String ADD_CART = "addCart";
	private static final String UPDATE_ORDER_STATUS = "updateOrderStatus";
	
    public void saveGoodsRankings(JoinPoint point) {
    	log.info("parameter: {}", Arrays.toString(point.getArgs()));
	    // 加入购物车、支付订单、评价才增加排行榜信息
	    if (!ADD_CART.equals( point.getSignature().getName()) && !UPDATE_ORDER_STATUS.equals( point.getSignature().getName())) {
			return;
		}
	    GoodsRankingsDTO dto = new GoodsRankingsDTO();
	    if (ADD_CART.equals( point.getSignature().getName())) {
	    	CartAddReq cartAddReq =(CartAddReq)point.getArgs()[0];
	    	dto.setProductId(cartAddReq.getProduct_id());
	    	dto.setMemberId(cartAddReq.getMember_id());
	    	if (StringUtils.isNoneBlank(cartAddReq.getNum())) {
	    		dto.setNum(Long.valueOf(cartAddReq.getNum()));
			}
	    	dto.setObjType(GoodsRankingsType.CART);
		} else if (UPDATE_ORDER_STATUS.equals( point.getSignature().getName())) {
			UpdateOrderStatusRequestDTO updateOrderStatusRequestDTO =(UpdateOrderStatusRequestDTO)point.getArgs()[0];
			// 付款
			if (!ActionFlowType.ORDER_HANDLER_ZF.equals(updateOrderStatusRequestDTO.getFlowType())
				&& !ActionFlowType.ORDER_HANDLER_PJ.equals(updateOrderStatusRequestDTO.getFlowType())) {
				return;
			}
			if (ActionFlowType.ORDER_HANDLER_ZF.equals(updateOrderStatusRequestDTO.getFlowType())) {
				dto.setObjType(GoodsRankingsType.DEAL);
			}
			if (ActionFlowType.ORDER_HANDLER_PJ.equals(updateOrderStatusRequestDTO.getFlowType())) {
				dto.setObjType(GoodsRankingsType.COMMENT);
			}
			
			dto.setOrderId(updateOrderStatusRequestDTO.getOrderId());
			dto.setSessionId(updateOrderStatusRequestDTO.getUserSessionId());
			
		}
	    goodsRankingsService.saveGoodsRankings(dto);
    }

    @AfterReturning(value = "execution(* com.iwhalecloud.retail.oms.web.controller.*.*(..))", returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint point, Object keys) {
    	MethodSignature signature = (MethodSignature) point.getSignature();
	    Method method = signature.getMethod();
	    boolean annotationPresent = method.isAnnotationPresent(GoodsRankingsAnnotation.class);
	    // 没有GoodsRankingsAnnotation注解的不添增加排行榜信息
	    if (!annotationPresent) {
			return;
		}
	    // 只有方法返回成功的添加增加排行榜信息
        if (keys instanceof ResultVO) {
            ResultVO resultVO = (ResultVO) keys;
            String resultCode = resultVO.getResultCode();
            if(ResultCodeEnum.SUCCESS.getCode().equals(resultCode)){
            	saveGoodsRankings(point);
            }
        }
    }
	 
}
