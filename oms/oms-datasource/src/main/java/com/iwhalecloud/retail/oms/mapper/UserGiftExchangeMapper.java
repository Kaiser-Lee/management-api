package com.iwhalecloud.retail.oms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeDTO;
import com.iwhalecloud.retail.oms.entity.gift.UserGiftExchange;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户奖品兑换
 */
@Mapper
public interface UserGiftExchangeMapper extends BaseMapper<UserGiftExchange> {

    List<UserGiftExchangeRespDTO> listUserGiftExchange(ListUserGiftExchangeDTO t);
    
    UserGiftExchangeRespDTO getNewestExchange();


}
