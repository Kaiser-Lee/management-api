package com.iwhalecloud.retail.oms.web.controller.rop;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import params.req.PartnerShopPageReq;
import params.resp.PartnerShopPageResp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.service.PartnerShopService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/rop/partnerShop")
public class PartnerShopROPController {

	@Reference
    private PartnerShopService partnerShopService;

    @ApiOperation(value = "根据条件查询厅店分页信息", notes = "根据条件查询厅店分页信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryPartnerShopPageByParam", method = RequestMethod.POST)
    public ResultVO qryPartnerShopPageByParam(@RequestBody PartnerShopPageReq req) {
        ResultVO resultVO = new ResultVO();
        log.info("PartnerShopPageReq:{}", JSON.toJSONString(req));
        try {
            ResultVO<PartnerShopPageResp> partnerShopPageResp = partnerShopService.qryPartnerShopPageByParam(req);
            if(partnerShopPageResp.getResultData()!=null){
                resultVO.setResultData(partnerShopPageResp.getResultData());
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }



}
