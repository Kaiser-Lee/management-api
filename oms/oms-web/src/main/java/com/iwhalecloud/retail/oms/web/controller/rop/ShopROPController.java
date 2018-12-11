package com.iwhalecloud.retail.oms.web.controller.rop;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import params.req.PartnerByIdReq;
import params.req.PartnerShopListReq;
import params.resp.PartnerByIdResp;
import params.resp.PartnerShopListResp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.resquest.ShopQueryDTO;
import com.iwhalecloud.retail.oms.service.ShopService;
import com.iwhalecloud.retail.oms.web.controller.BaseController;

/**
 * @Author pjq
 * @Date 2018/10/17
 **/
@RestController
@RequestMapping("/api/rop/shop")
@Slf4j
public class ShopROPController extends BaseController{

	@Reference
    private ShopService shopService;

    @ApiOperation(value = "获取店铺详情", notes = "根据partnerId，获取店铺详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "partnerId", value = "分销商ID", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping(value="detailShop")
    public ResultVO<PartnerByIdResp> detailShop(@RequestParam(value = "partnerId", required=false) String partnerId){
        log.info("ShopController detailShop partnerId={} ",partnerId);
        PartnerByIdReq req = new PartnerByIdReq();
        if(StringUtils.isEmpty(partnerId)){
            return failResultVO("partnerId is null");
        }
        req.setPartnerId(partnerId);
        ResultVO<PartnerByIdResp> resultVO = shopService.getShopById(req);
        log.info("ShopController detailShop resultVO={} ",resultVO);
        return resultVO;
    }
}
