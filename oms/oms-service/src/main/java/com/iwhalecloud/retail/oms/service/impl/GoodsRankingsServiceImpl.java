package com.iwhalecloud.retail.oms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.GoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.entity.GoodsRankingsDO;
import com.iwhalecloud.retail.oms.manager.GoodsRankingsManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.service.GoodsRankingsService;

import java.util.List;

@Service
public class GoodsRankingsServiceImpl implements GoodsRankingsService {

    @Autowired
    private GoodsRankingsManager goodsRankingsManager;


    @Override
    public boolean saveGoodsRankings(GoodsRankingsDTO goodsRankingsDTO) {
        GoodsRankingsDO goodsRankingsDO = new GoodsRankingsDO() ;
        BeanUtils.copyProperties(goodsRankingsDTO, goodsRankingsDO);
        int i = goodsRankingsManager.insert(goodsRankingsDO);
        boolean ret = false;
        if(i>0){
            ret = true;
        }
        return ret;
    }

    @Override
    public List<ListGoodsRankingsDTO> listGoodsRankings(String objType, String goodsId, String goodsName, int pageIndex, int pageSize) {
        List<ListGoodsRankingsDTO> listOrderCartDTO = goodsRankingsManager.listOrderCart(objType, goodsId, goodsName, pageIndex, pageSize);
        return listOrderCartDTO;
    }
}
