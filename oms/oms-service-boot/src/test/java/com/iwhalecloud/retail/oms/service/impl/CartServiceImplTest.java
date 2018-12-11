package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.OmsServiceApplication;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.entity.EntityCache;
import com.iwhalecloud.retail.oms.service.CartService;
import com.iwhalecloud.retail.oms.service.ICacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import params.cart.req.CartListReq;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsServiceApplication.class)
public class CartServiceImplTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private ICacheService iCacheService;
    @Test
    public void listCart() {
        CartListReq req = new CartListReq();
        req.setMember_id("181031588100419686");
        req.setUserSessionId("55ac0548939d4f63ac0249412fdcec20");
        ResultVO resultVO = cartService.listCart(req);
        System.out.println(resultVO.getResultCode());
    }

}
