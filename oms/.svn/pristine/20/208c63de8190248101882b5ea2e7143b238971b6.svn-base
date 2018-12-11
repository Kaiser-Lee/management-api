package com.iwhalecloud.retail.oms.web.controller.rop;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;
import com.iwhalecloud.retail.oms.service.GoodsEvaluateTotalService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/goodsEvaluate")
public class GoodsEvaluateController {

	@Reference
    private GoodsEvaluateTotalService goodsEvaluateTotalService;

    @RequestMapping("/selectEvaluateByGoodsId")
    public ResultVO selectOrder(@RequestBody TGoodsEvaluateTotalDTO request) {
        ResultVO resultVO = new ResultVO();
        log.info("gongS request{}", JSON.toJSONString(request));
        try {
            List<TGoodsEvaluateTotalDTO> resp = goodsEvaluateTotalService.selectGoodsEvaluate(request);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(resp);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }

}
