package com.iwhalecloud.retail.oms.service.impl.gift;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.UserGiftExchangeDTO;
import com.iwhalecloud.retail.oms.entity.gift.UserGiftExchange;
import com.iwhalecloud.retail.oms.entity.gift.UserPointRecord;
import com.iwhalecloud.retail.oms.manager.GiftManager;
import com.iwhalecloud.retail.oms.manager.UserGiftExchangeManager;
import com.iwhalecloud.retail.oms.manager.UserPointRecordManager;
import com.iwhalecloud.retail.oms.service.gift.UserGiftExchangeService;

/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户奖品兑换
 */
@Service
@Slf4j
public class UserGiftExchangeServiceImpl implements UserGiftExchangeService {

	@Autowired
    private UserGiftExchangeManager userGiftExchangeManager;
    @Autowired
    private UserPointRecordManager userPointRecordManager;
    
    @Autowired
    private GiftManager giftManager;
    


	@Override
	public List<UserGiftExchangeRespDTO> listUserPointRecord(ListUserGiftExchangeDTO t) {
		Integer pageOffset = t.getPageNo() * t.getPageSize() + t.getPageSize();
    	Integer pageStartset = t.getPageNo() * t.getPageSize();
		t.setPageOffset(pageOffset);
		t.setPageStartset(pageStartset);
		return userGiftExchangeManager.listUserGiftExchange(t);
	}


	@Transactional
	@Override
	public synchronized Integer saveUserGiftExchange(UserGiftExchangeDTO t) {
		UserPointRecordRespDTO userNewestRecord = userPointRecordManager.getUserPoint(t.getUserId());
		GiftRespDTO gift = giftManager.findById(t.getGiftId());
		// 没找到奖品或是奖品下架
		if (null == gift || 1 == gift.getIsDown()) {
			return -1;
		}
		// 没有积分记录
		if (null == userNewestRecord) {
			return -1;
		}
		Date effDate = gift.getEffDate();
		Date expDate = gift.getExpDate();
		Date now = new Date();
		// 当前时间不在兑奖区
		if (now.before(effDate) || now.after(expDate)) {
			return -1;
		}
		Integer needTotalPoint = t.getGainAmount()*gift.getNeedPointAmount();
		Integer leftTotalPoint = userNewestRecord.getTotalPoint() - needTotalPoint;
		// 积分不够
		if (leftTotalPoint < 0) {
			return -1;
		}
		
		UserPointRecord userPointRecordDTO = new UserPointRecord();
		userPointRecordDTO.setChangePoint(-needTotalPoint);
		userPointRecordDTO.setUserId(t.getUserId());
		userPointRecordDTO.setSrcType(1);
		userPointRecordDTO.setCreateDate(now);
		userPointRecordDTO.setTotalPoint(leftTotalPoint);
		userPointRecordDTO.setRemark("兑换奖品");
		Integer insertRecord = userPointRecordManager.insert(userPointRecordDTO);
		// 积分记录插入失败
		if (null == insertRecord || insertRecord < 0) {
			return -1;
		}
		
		UserGiftExchange userGiftExchange = new UserGiftExchange();
		userGiftExchange.setGainAmount(t.getGainAmount());
		userGiftExchange.setGiftId(t.getGiftId());
		userGiftExchange.setGiftName(gift.getGiftName());
		userGiftExchange.setGiftType(gift.getGiftType());
		userGiftExchange.setNeedPointAmount(gift.getNeedPointAmount());
		userGiftExchange.setTotalPoint(leftTotalPoint);
		userGiftExchange.setUserId(t.getUserId());
		userGiftExchange.setCreateDate(now);
		
		Integer insertExchange = userGiftExchangeManager.insert(userGiftExchange);
		return insertExchange;
	}

}
