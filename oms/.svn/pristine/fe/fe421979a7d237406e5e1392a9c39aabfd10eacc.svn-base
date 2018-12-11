package com.iwhalecloud.retail.oms.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.entity.gift.UserPointRecord;
import com.iwhalecloud.retail.oms.mapper.UserPointRecordMapper;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户流水记录
 */
@Component
public class UserPointRecordManager {

    @Resource
    private UserPointRecordMapper userPointRecordMapper;

    public Integer insert(UserPointRecord dto) {
        Integer t = userPointRecordMapper.insert(dto);
        return t;
    }


    public List<UserPointRecordRespDTO> listUserPointRecord(UserPointRecordRespDTO t){
    	return userPointRecordMapper.listUserPointRecord(t);
    }
    
    public UserPointRecordRespDTO getUserPoint(Long userId){
    	return userPointRecordMapper.getUserPoint(userId);
    }
    
}
