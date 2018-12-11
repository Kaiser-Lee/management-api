package com.iwhalecloud.retail.oms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.entity.gift.UserPointRecord;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户流水记录
 */
@Mapper
public interface UserPointRecordMapper extends BaseMapper<UserPointRecord> {

    List<UserPointRecordRespDTO> listUserPointRecord(UserPointRecordRespDTO t);
    
    UserPointRecordRespDTO getUserPoint(Long userId);

}
