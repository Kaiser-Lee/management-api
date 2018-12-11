package com.iwhalecloud.retail.oms.service.gift;

import java.util.List;

import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftDTO;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
public interface GiftService {

    /**
     * 获取奖品
     * @param t
     * @return
     */
    List<GiftRespDTO> listGift(ListGiftDTO t);


}
