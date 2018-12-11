package com.iwhalecloud.retail.oms.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBasePageReq;
import com.iwhalecloud.retail.oms.web.controller.BaseController;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.partner.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.partner.service.PartnerService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/partner")
public class PartnerController extends BaseController {
	
    @Reference
    private PartnerService partnerService;


    @ApiOperation(value = "可供代理商查询", notes = "可供代理商查询返回信息分页")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/querySupplyRel", method = RequestMethod.POST)
    ResultVO<Page<PartnerDTO>> querySupplyRel(@RequestBody @ApiParam( value = "supplierReq", required = true )SupplierReq supplierReq)throws Exception{
        return  partnerService.querySupplyRel(supplierReq);
    }


    /**
     * 代理商列表分页查询接口.
     *
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    @ApiOperation(value = "代理商列表分页查询接口", notes = "根据在页面上选择的代理商名称，状态查询代理商列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryPagelist", method = RequestMethod.POST)
    public ResultVO<Page<PartnerDTO>> qryPartnerPageList(@RequestBody @ApiParam( value = "查询条件", required = true )PartnerPageReq page) {
        ResultVO<Page<PartnerDTO>> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(page));
        Page<PartnerDTO> res = partnerService.qryPartnerPageList(page);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(res);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
}