package com.iwhalecloud.retail.oms.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.partner.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelCreateReq;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelDeleteReq;
import com.iwhalecloud.retail.partner.service.SupplyRelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/supplyRel")
public class SupplyRelController {
	
    @Reference
    private SupplyRelService deleteSupplyrel;


    @ApiOperation(value = "可供代理商删除绑定", notes = "可供代理商删除绑定")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteSupplyrel", method = RequestMethod.POST)
    ResultVO deleteSupplyrel(@RequestBody @ApiParam( value = "supplyRelDeleteReq", required = true ) SupplyRelDeleteReq supplyRelDeleteReq)throws Exception{
        return  deleteSupplyrel.deleteSupplyrel(supplyRelDeleteReq);
    }

    @ApiOperation(value = "可供代理商绑定", notes = "可供代理商绑定")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/createSupplyrel", method = RequestMethod.POST)
    ResultVO createSupplyrel(@RequestBody @ApiParam( value = "supplyRelCreateReq", required = true ) SupplyRelCreateReq supplyRelCreateReq)throws Exception{
        return  deleteSupplyrel.createSupplyrel(supplyRelCreateReq);
    }

}