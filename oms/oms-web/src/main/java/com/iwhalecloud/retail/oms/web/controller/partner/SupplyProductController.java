package com.iwhalecloud.retail.oms.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.web.controller.BaseController;
import com.iwhalecloud.retail.partner.dto.SupplyProductDTO;
import com.iwhalecloud.retail.partner.dto.resp.SupplyProductQryResp;
import com.iwhalecloud.retail.partner.exception.BaseException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.partner.service.SupplyProductService;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/supplyProduct")
public class SupplyProductController extends BaseController {

    @Reference
    private SupplyProductService supplyProductService;

    @ApiOperation(value = "分页查询可供产品", notes = "根据supplierId，分页查询可供产品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //分页查询可供产品
    @RequestMapping(value = "/querySupplyProduct", method = RequestMethod.POST)
    public ResultVO<Page<SupplyProductQryResp>> querySupplyProduct(@RequestBody @ApiParam(value = "查询条件", required = true) SupplyProductDTO dto) {
        ResultVO<Page<SupplyProductQryResp>> resultVO = new ResultVO<>();
        //Page<SupplyProductQryResp> supplyProductDTO = supplyProductService.querySupplyProduct(dto);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        //resultVO.setResultData(supplyProductDTO);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    @ApiOperation(value = "绑定可供产品", notes = "根据supplierId、productId，绑定可供产品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //绑定可供产品
    @RequestMapping(value = "/bindSupplyProduct", method = RequestMethod.POST)
    public ResultVO bindSupplyProduct(@RequestBody @ApiParam(value = "查询条件", required = true) List<SupplyProductDTO> supplyList) {
        ResultVO resultVO = new ResultVO<>();
        for (int i = 0; i < supplyList.size(); i++) {
            supplyProductService.bindSupplyProduct(supplyList.get(i));
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData("绑定成功");
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    @ApiOperation(value = "解除绑定可供产品", notes = "根据relId，解除绑定可供产品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //解除绑定可供产品
    @RequestMapping(value = "/unBindSupplyProduct", method = RequestMethod.DELETE)
    public ResultVO unBindSupplyProduct(@RequestBody @ApiParam(value = "查询条件", required = true) List<SupplyProductDTO> supplyList) throws ParseException, BaseException {
        ResultVO resultVO = new ResultVO<>();
        for (int i = 0; i < supplyList.size(); i++) {
            supplyProductService.unBindSupplyProduct(supplyList.get(i));
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData("解除绑定成功");
        resultVO.setResultMsg("成功");
        return resultVO;
    }
}