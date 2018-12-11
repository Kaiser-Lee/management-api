package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.ResultVO;
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
public interface TempCartService {

    /**
     * 临时添加购物车
     * @param key
     * @param value
     * @return
     */
    boolean tempAddCart(String key,String value);

    /**
     * 临时取购物车
     * @param key
     * @return
     */
    String getTempCart(String key);

    /**
     * 查询临时购物车
     * @param key
     * @return
     */
    boolean selectTempCart(String key);
}
