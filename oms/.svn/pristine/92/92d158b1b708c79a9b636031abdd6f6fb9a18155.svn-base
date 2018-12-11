package com.iwhalecloud.retail.oms.service.impl.gift;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftDTO;
import com.iwhalecloud.retail.oms.manager.GiftManager;
import com.iwhalecloud.retail.oms.service.gift.GiftService;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
@Service
@Slf4j
public class GiftServiceImpl implements GiftService {

    @Autowired
    private GiftManager giftManager;


    @Override
    public List<GiftRespDTO> listGift(ListGiftDTO t) {
    	Integer pageOffset = t.getPageNo() * t.getPageSize() + t.getPageSize();
    	Integer pageStartset = t.getPageNo() * t.getPageSize();
		t.setPageOffset(pageOffset);
		t.setPageStartset(pageStartset);
        return giftManager.listGift(t);
    }

}
