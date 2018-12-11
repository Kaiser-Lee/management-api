package com.iwhalecloud.retail.oms.service.gift;

import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.entity.gift.UserPointRecord;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户流水记录
 */
public interface UserPointRecordService {

    /**
     * 添加用户积分记录
     * @param t
     * @return
     */
	Integer saveUserPointRecord(UserPointRecord t);


    /**
     * 获取用户积分（最新的）
     * @param userId
     * @return
     */
    UserPointRecordRespDTO getUserPoint(Long userId);

}
