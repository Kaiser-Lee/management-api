package com.iwhalecloud.retail.oms.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftDTO;
import com.iwhalecloud.retail.oms.mapper.GiftMapper;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
@Component
public class GiftManager {

    @Resource
    private GiftMapper giftMapper;


    public List<GiftRespDTO> listGift(ListGiftDTO t) {

        List<GiftRespDTO> list = giftMapper.listGift(t);
        return list;
    }
    
    public GiftRespDTO findById(Long giftId) {
    	
    	GiftRespDTO gift = giftMapper.getById(giftId);
    	return gift;
    }
}
