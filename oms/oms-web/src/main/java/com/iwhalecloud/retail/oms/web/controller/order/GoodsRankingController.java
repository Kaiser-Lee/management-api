package com.iwhalecloud.retail.oms.web.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.service.GoodsRankingsService;
import com.iwhalecloud.retail.partner.dto.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/order/goodRanking")
public class GoodsRankingController {

    @Reference
    private GoodsRankingsService goodsRankingsService;


    @GetMapping("/listGoodRanking")
    public ResultVO listGoodsRanking(@RequestParam(value = "objType") String objType, @RequestParam(value = "goodsId") String goodsId, @RequestParam(value = "goodsName") String goodsName,@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize) {
        ResultVO resultVO = new ResultVO();
        List<ListGoodsRankingsDTO> listGoodsRankingsDTO  = goodsRankingsService.listGoodsRankings(objType, goodsId, goodsName, pageIndex, pageSize);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(listGoodsRankingsDTO);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
}