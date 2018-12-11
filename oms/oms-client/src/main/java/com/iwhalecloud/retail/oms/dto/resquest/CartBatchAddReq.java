package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;
import params.cart.req.CartAddReq;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/12/1
 **/
@Data
public class CartBatchAddReq implements Serializable {

    List<CartAddReq> cartAddReqList;
}
