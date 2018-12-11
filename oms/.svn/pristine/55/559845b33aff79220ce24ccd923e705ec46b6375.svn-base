package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.entity.GoodsRankingsDO;
import com.iwhalecloud.retail.oms.mapper.GoodsRankingsMapper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import java.util.List;


@Component
public class GoodsRankingsManager{
    @Resource
    private GoodsRankingsMapper goodsRankingsMapper;

    public int insert(GoodsRankingsDO goodsRankingsDO){
        return goodsRankingsMapper.insert(goodsRankingsDO);
    }

    public int saveOrderCart(GoodsRankingsDO goodsRankingsDO){
        return goodsRankingsMapper.saveOrderCart(goodsRankingsDO);
    }

   public List<ListGoodsRankingsDTO> listOrderCart(String objType, String goodsId, String goodsName, int pageIndex, int pageSize){
       List<ListGoodsRankingsDTO> listGoodsRankingsDTOS = goodsRankingsMapper.listGoodsRankings(objType, goodsId, goodsName, pageIndex, pageSize);
        return listGoodsRankingsDTOS;
    }


}
