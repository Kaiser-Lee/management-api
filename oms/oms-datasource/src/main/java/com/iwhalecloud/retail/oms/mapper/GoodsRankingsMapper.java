package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.entity.GoodsRankingsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: GoodsRankingsMapper
 * @author autoCreate
 */
@Mapper
public interface GoodsRankingsMapper extends BaseMapper<GoodsRankingsDO>{

    public int saveOrderCart(GoodsRankingsDO orderCartDO);

    public List<ListGoodsRankingsDTO> listGoodsRankings(@Param("objType")String objType, @Param("goodsId")String goodsId, @Param("goodsName")String goodsName, @Param("pageIndex")int pageIndex, @Param("pageSize")int pageSize);
}
