package com.iwhalecloud.retail.oms.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeDTO;
import com.iwhalecloud.retail.oms.entity.gift.UserGiftExchange;
import com.iwhalecloud.retail.oms.mapper.UserGiftExchangeMapper;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户奖品兑换
 */
@Component
public class UserGiftExchangeManager {

    @Resource
    private UserGiftExchangeMapper userGiftExchangeMapper;

    public Integer insert(UserGiftExchange dto) {
        Integer t = userGiftExchangeMapper.insert(dto);
        return t;
    }


    public List<UserGiftExchangeRespDTO> listUserGiftExchange(ListUserGiftExchangeDTO t){
    	return userGiftExchangeMapper.listUserGiftExchange(t);
    }
    
    public UserGiftExchangeRespDTO getNewestExchange(){
    	return userGiftExchangeMapper.getNewestExchange();
    }
    
}
