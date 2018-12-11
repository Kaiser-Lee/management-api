package com.iwhalecloud.retail.oms.web.controller.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.dto.GoodsRelDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/goodsRel")
public class GoodsRelController {

    @Reference
    private GoodsRelService goodsRelService;


    @PostMapping("/addGoodsRel")
    public ResultVO addGoodsRel(@RequestParam(value = "aGoodsId") String aGoodsId, @RequestParam(value = "zGoodsId") String zGoodsId) {
        List<GoodsRelDTO> goodsRelList = Lists.newArrayList();
        GoodsRelDTO goodsRel = new GoodsRelDTO();
        goodsRel.setAGoodsId(aGoodsId);
        goodsRel.setZGoodsId(zGoodsId);
        goodsRelList.add(goodsRel);
        return goodsRelService.addGoodsRel(goodsRelList);
    }

    @GetMapping("/getRecommendGoodsByGoodsId")
    public ResultVO getRecommendGoodsByGoodsId(@RequestParam(value = "goodsId") String goodsId) {
        return goodsRelService.getRecommendGoodsByGoodsId(goodsId);
    }
}