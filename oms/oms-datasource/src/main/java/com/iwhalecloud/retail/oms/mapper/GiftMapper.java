package com.iwhalecloud.retail.oms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftDTO;
import com.iwhalecloud.retail.oms.entity.gift.Gift;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
@Mapper
public interface GiftMapper extends BaseMapper<Gift> {

    List<GiftRespDTO> listGift(ListGiftDTO tGiftDTO);

    
    GiftRespDTO getById(Long giftId);
}
