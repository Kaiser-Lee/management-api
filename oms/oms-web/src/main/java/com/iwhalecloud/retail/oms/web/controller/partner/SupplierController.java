package com.iwhalecloud.retail.oms.web.controller.partner;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.req.SupplierQueryReq;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.web.controller.BaseController;
import com.iwhalecloud.retail.partner.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.service.SupplierService;

/**
 * @author mzl
 * @date 2018/10/29
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/api/supplier")
@Slf4j
public class SupplierController extends BaseController {

    @Reference
    private SupplierService supplierService;


    @GetMapping("/listSupplier")
    public ResultVO<List<SupplierDTO>> listSupplier() {
        return supplierService.listSupplier();
    }

    @ApiOperation(value = "供应商查询", notes = "供应商查询返回信息分页")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/querySupplier", method = RequestMethod.POST)
    ResultVO<Page<SupplierDTO>> querySupplier(@RequestBody @ApiParam( value = "supplierQueryReq", required = true ) SupplierQueryReq supplierQueryReq)throws Exception{
        return  supplierService.querySupplier(supplierQueryReq);
    }

}
