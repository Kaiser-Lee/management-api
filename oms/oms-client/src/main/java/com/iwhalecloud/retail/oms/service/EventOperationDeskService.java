package com.iwhalecloud.retail.oms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventOperationDeskService {

    int getTodayEventCountByPartnerId(String partnerId,String eventCode);

    int getTodayEventCountByArea(String adscriptionCity,String eventCode);

    List<Map<String,Object>> getShopVisitVolumeRankByArea(String adscriptionCity, Date beginTime, Date endTime);

    List<Map<String,Object>> getShopWorkTimeRankByArea(String adscriptionCity,Date beginTime,Date endTime);

    List<Map<String,Object>> getTimeIntervalVisitByArea(String adscriptionCity,Date beginTime,Date endTime);

    List<Map<String, Object>> getOnOffLineTimeAboutDevice(String adscriptionCity, Date beginTime, Date endTime) throws Exception;
}
