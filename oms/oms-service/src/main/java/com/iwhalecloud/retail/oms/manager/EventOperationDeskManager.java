package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.mapper.EventOperationDeskMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class EventOperationDeskManager {

    private static final DateFormat DATE_FORMAT_TRUNC = new SimpleDateFormat("yyyy/MM/dd 00:00:00");

    @Resource
    private EventOperationDeskMapper eventOperationDeskMapper;

    public int getTodayEventCountByPartnerId(String partnerId, String eventCode){
        return eventOperationDeskMapper.getTodayEventCountByPartnerId(partnerId, eventCode);
    }

    public int getTodayEventCountByArea(String adscriptionCity, String eventCode) {
        return eventOperationDeskMapper.getTodayEventCountByArea(adscriptionCity, eventCode);
    }

    public List<Map<String,Object>> getShopVisitVolumeRankByArea(String adscriptionCity, Date beginTime, Date endTime){
        return eventOperationDeskMapper.getShopVisitVolumeRankByArea(adscriptionCity, beginTime, endTime);
    }

    public  List<Map<String,Object>> getShopWorkTimeRankByArea(String adscriptionCity,Date beginTime,Date endTime){
        return eventOperationDeskMapper.getShopWorkTimeRankByArea(adscriptionCity, beginTime, endTime);
    }

    public List<Map<String, Object>> getTimeIntervalVisitByArea(String adscriptionCity, Date beginTime, Date endTime) {
        return eventOperationDeskMapper.getTimeIntervalVisitByArea(adscriptionCity, beginTime, endTime);
    }

    public List<Map<String, Object>> getOnOffLineTimeAboutDevice(String adscriptionCity, Date beginTime, Date endTime) throws Exception{
        List<Map<String, Long>> onOffLineTimeList = eventOperationDeskMapper.getOnOffLineTimeAboutDevice(adscriptionCity, beginTime, endTime);
        // 取整
        long beginTimeL = DATE_FORMAT_TRUNC.parse(DATE_FORMAT_TRUNC.format(beginTime)).getTime();
        long endTimeL = DATE_FORMAT_TRUNC.parse(DATE_FORMAT_TRUNC.format(endTime)).getTime();
        Map<Long,Double> timeIntervalMap = new HashMap<>();
        do{
            timeIntervalMap.put(beginTimeL,0.0);
            // 增加一天
            beginTimeL = beginTimeL + 24*60*60*1000;
        }
        while (beginTimeL <= endTimeL);
        //拆箱
        for (Map<String,Long> timeMap : onOffLineTimeList){
            long onlineTimeL = timeMap.get("ONLINE_TIME");
            long offlineTimeL = timeMap.get("OFFLINE_TIME");
            for (Map.Entry entry : timeIntervalMap.entrySet()){
                handleTimeData(entry,onlineTimeL,offlineTimeL);
            }
        }
        List<Map<String,Object>> jsonMapList = convertToJSONMapList(timeIntervalMap);
        return jsonMapList;
    }

    /**
     * 转化为输出JSON的数据结构
     * @param timeIntervalMap
     * @return
     */
    private List<Map<String,Object>> convertToJSONMapList(Map<Long,Double> timeIntervalMap){
        List<Map<String,Object>> jsonMapList = new ArrayList<>();
        for(Map.Entry entry : timeIntervalMap.entrySet()){
            Map<String,Object> entryMap = new HashMap<>();
            entryMap.put("TIME_INTERVAL",((Long) entry.getKey()).doubleValue());
            entryMap.put("AMOUNT_TOTAL",entry.getValue());
            jsonMapList.add(entryMap);
        }
        return jsonMapList;
    }

    /**
     * 计算各时段工作时长叠加总值
     * @param entry
     * @param onlineTimeL
     * @param offlineTimeL
     */
    private void handleTimeData(Map.Entry<Long,Double> entry, long onlineTimeL, long offlineTimeL){
        long beginTimeL = entry.getKey();
        long endTimeL = beginTimeL + 24*60*60*1000;
        long less = 0;
        double total = entry.getValue();
        if (onlineTimeL <= beginTimeL && beginTimeL <= offlineTimeL && offlineTimeL <= endTimeL){
            less = offlineTimeL - beginTimeL;
        } else if (beginTimeL <= onlineTimeL && onlineTimeL <= endTimeL && endTimeL <= offlineTimeL){
            less = endTimeL - onlineTimeL;
        } else if (beginTimeL <= onlineTimeL && onlineTimeL <= offlineTimeL && offlineTimeL <= endTimeL){
            less = offlineTimeL - onlineTimeL;
        } else if (onlineTimeL <= beginTimeL && beginTimeL <= endTimeL && endTimeL <= offlineTimeL){
            less = endTimeL - beginTimeL;
        }
        BigDecimal lessBd = new BigDecimal(less);
        BigDecimal hoursBd = lessBd.divide(new BigDecimal(60*60*1000));
        total =+ (hoursBd.doubleValue());
        entry.setValue(total);
    }
}
