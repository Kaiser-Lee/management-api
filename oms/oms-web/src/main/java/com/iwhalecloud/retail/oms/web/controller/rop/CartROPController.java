package com.iwhalecloud.retail.oms.web.controller.rop;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.VO.CartVO;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.resquest.CartBatchAddReq;
import com.iwhalecloud.retail.oms.dto.resquest.TempCartReq;
import com.iwhalecloud.retail.oms.service.CartService;
import com.iwhalecloud.retail.oms.service.TempCartService;
import com.iwhalecloud.retail.oms.web.annotation.GoodsRankingsAnnotation;
import com.iwhalecloud.retail.oms.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.oms.web.controller.BaseController;
import com.iwhalecloud.retail.oms.web.interceptor.MemberContext;
import com.ztesoft.net.mall.core.model.support.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import params.cart.req.CartAddReq;
import params.cart.req.CartListReq;
import params.cart.req.CartReverseSelectionReq;
import params.cart.resp.CartAddResp;
import zte.params.cart.req.CartDeleteReq;
import zte.params.order.req.CartUpdateReq;

import java.util.List;

/**
 * @author pjq
 * @date 2018/10/8
 */
@RestController
@RequestMapping("/api/rop/cart")
@CrossOrigin
@Slf4j
public class CartROPController  extends BaseController {

    @Reference
    private CartService cartService;
    @Reference
    private TempCartService tempCartService;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    /**
     * 查询购物车
     * @return
     */
    @GetMapping(value="getCartList")
    @UserLoginToken
    public ResultVO getCartList(@RequestParam String partnerId){
        CartListReq req = new CartListReq();
        //获取memberId
        String memberId = MemberContext.getMemberId();
        String sessionId = MemberContext.getUserSessionId();
        if(StringUtils.isEmpty(memberId)){
            return failResultVO();
        }
        if(StringUtils.isEmpty(partnerId)){
            return failResultVO("partnerId must not be null");
        }
        if(StringUtils.isNotEmpty(memberId)){
            req.setMember_id(memberId);
        }
        req.setUserSessionId(sessionId);
        req.setPartnerId(partnerId);
        ResultVO respResultVO = cartService.listCart(req);
        CartVO cartVO =(CartVO)respResultVO.getResultData();
        List<CartItem> cartItems = cartVO.getGoodsItemList();
        for(CartItem cartItem:cartItems){
            if(StringUtils.isEmpty(cartItem.getImage_default())){
                continue;
            }
            String newImageFile = fullImageUrl(cartItem.getImage_default(),dfsShowIp,true);
            cartItem.setImage_default(newImageFile);
        }
        MemberContext.removeMemberSession();
        return respResultVO;
    }

    /**
     * 添加购物车
     * @param req
     * @return
     */
    @RequestMapping(value="/addCart",method = RequestMethod.POST)
    @UserLoginToken
    @GoodsRankingsAnnotation
    public ResultVO addCart(@RequestBody CartAddReq req){
        log.info("CartController addCart req={} ",req);
        if(null == req){
            return failResultVO();
        }
        if(StringUtils.isEmpty(req.getPartnerId())){
            return failResultVO("partnerId must not be null");
        }
        String sessionId = MemberContext.getUserSessionId();
        String memeberId = MemberContext.getMemberId();
        req.setMember_id(memeberId);
        req.setUserSessionId(sessionId);
        CartAddResp resp= cartService.addCart(req);
        if (org.springframework.util.StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getCart());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }
    @RequestMapping(value="/batchAddCart",method = RequestMethod.POST)
    @UserLoginToken
    @GoodsRankingsAnnotation
    public ResultVO batchAddCart(@RequestBody CartBatchAddReq req){
        log.info("CartController batchAddCart req={} ",req);
        if(null == req){
            return failResultVO();
        }
        if(CollectionUtils.isEmpty(req.getCartAddReqList())){
            return failResultVO();
        }
        List<CartAddReq> cartAddReqList = req.getCartAddReqList();
        for(CartAddReq cartAddReq : cartAddReqList){
            String sessionId = MemberContext.getUserSessionId();
            String memeberId = MemberContext.getMemberId();
            cartAddReq.setMember_id(memeberId);
            cartAddReq.setUserSessionId(sessionId);
        }
        return cartService.batchAddCart(cartAddReqList);
    }
    /**
     * 修改购物车数量
     * @param req
     * @return
     */
    @RequestMapping(value="/updateCartNum",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> updateCartNum(@RequestBody CartUpdateReq req){
        log.info("CartController updateCartNum req={} ",req);
        if(null == req){
            return failResultVO();
        }
        if(StringUtils.isEmpty(req.getPartnerId())){
            return failResultVO("partnerId must not be null");
        }
        String sessionId = MemberContext.getUserSessionId();
        req.setUserSessionId(sessionId);
        ResultVO<Boolean> resultVO = cartService.updateCartNum(req);
        return resultVO;
    }

    /**
     * 删除购物车
     * req
     * @return
     */
    @RequestMapping(value="/deleteCart",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> deleteCart(@RequestBody CartDeleteReq req){
        log.info("CartController updateCartNum req={} ",req);
        String sessionId = MemberContext.getUserSessionId();
        req.setUserSessionId(sessionId);
        ResultVO<Boolean> resultVO = cartService.deleteCart(req);
        return resultVO;
    }

    @GetMapping(value="/cartReverseSelection")
    @UserLoginToken
    public ResultVO<Boolean> cartReverseSelection(@RequestParam String partnerId){
        CartReverseSelectionReq req = new CartReverseSelectionReq();
        String sessionId = MemberContext.getUserSessionId();
        String memberId = MemberContext.getMemberId();
        req.setUserSessionId(sessionId);
        req.setMember_id(memberId);
        if(StringUtils.isEmpty(partnerId)){
            return failResultVO("partnerId must not be null");
        }
        ResultVO<Boolean> resultVO = cartService.cartReverseSelection(req);
        return resultVO;
    }
    @RequestMapping(value="/tempAddCart",method = RequestMethod.POST)
    public ResultVO<Boolean> tempAddCart(@RequestBody TempCartReq req){
        if(null == req){
            return ResultVO.error("req is null");
        }
        if(StringUtils.isEmpty(req.getKey())){
            return ResultVO.error("key is null");
        }
        if(StringUtils.isEmpty(req.getValue())){
            return ResultVO.error("value is null");
        }
        tempCartService.tempAddCart(req.getKey(),req.getValue());
        return ResultVO.success(true);
    }

    @GetMapping(value="/getTempCart")
    public ResultVO<String> getTempCart(@RequestParam String key){
        if(StringUtils.isEmpty(key)){
            return ResultVO.error("key is null");
        }
        Object result = tempCartService.getTempCart(key);
        if(null == result){
            return ResultVO.error("result is null");
        }
        return ResultVO.success(result.toString());
    }

    @GetMapping(value="/selectTempCart")
    public ResultVO<Boolean> selectTempCart(@RequestParam String key){
        if(StringUtils.isEmpty(key)){
            return ResultVO.error("key is null");
        }
        Boolean result = tempCartService.selectTempCart(key);
        return ResultVO.success(result);
    }

    /**
     * 拼接完整的地址
     * @param imagePath
     * @param showUrl
     * @param flag 为true时，拼接完整地址，为false时，是截取地址
     * @return
     */
    private static String fullImageUrl(String imagePath, String showUrl, boolean flag) {
        String aftPath = "";
        if (flag) {
            String[] pathArr = imagePath.split(",");
            for (String befPath : pathArr) {
                if (org.springframework.util.StringUtils.isEmpty(aftPath)) {
                    if (!befPath.startsWith("http")) {
                        aftPath += showUrl + befPath;
                    } else {
                        aftPath += befPath;
                    }
                } else {
                    if (!befPath.startsWith("http")) {
                        aftPath += "," + showUrl + befPath;
                    } else {
                        aftPath += "," + befPath;
                    }
                }
            }
        } else {
            if (!org.springframework.util.StringUtils.isEmpty(imagePath)) {
                aftPath = imagePath.replaceAll(showUrl, "");
            }
        }
        return aftPath;
    }
}
