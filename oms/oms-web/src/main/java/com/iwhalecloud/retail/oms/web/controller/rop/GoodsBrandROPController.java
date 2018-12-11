package com.iwhalecloud.retail.oms.web.controller.rop;

import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.service.dubbo.rop.GoodsBrandService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zte.params.brand.req.BrandAddReq;
import zte.params.brand.req.BrandDeleteReq;
import zte.params.brand.req.BrandGetReq;
import zte.params.brand.req.BrandUpdateReq;
import zte.params.brand.resp.BrandGetResp;
import zte.params.brand.resp.BrandListResp;
import zte.params.brand.resp.BrandOperateResp;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author mzl
 * @date 2018/10/23
 */
@RestController
@RequestMapping("/api/rop/goodsBrand")
@Slf4j
public class GoodsBrandROPController {

	@Reference
    private GoodsBrandService goodsBrandService;

    @GetMapping(value="getBrand")
    public ResultVO getBrand(@RequestParam(value = "brandId", required = false) String brandId,
                             @RequestParam(value = "goodsId", required = false) String goodsId) {
        log.info("GoodsBrandController.getBrand brandId={}, goodsId={}", brandId, goodsId);
        if (StringUtils.isEmpty(brandId) && StringUtils.isEmpty(goodsId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        BrandGetReq req = new BrandGetReq();
        req.setBrand_id(brandId);
        req.setGoods_id(goodsId);
        BrandGetResp resp = goodsBrandService.getBrand(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getBrand());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @GetMapping(value="listBrand")
    public ResultVO listBrand() {
        BrandListResp resp = goodsBrandService.listBrand();
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getBrands());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="addBrand")
    public ResultVO addBrand(@RequestBody BrandAddReq req) {
        log.info("GoodsBrandController.addBrand req={}", req);
        BrandOperateResp resp = goodsBrandService.addBrand(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="updateBrand")
    public ResultVO updateBrand(@RequestBody BrandUpdateReq req) {
        log.info("GoodsBrandController.updateBrand req={}", req);
        BrandOperateResp resp = goodsBrandService.updateBrand(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @PostMapping(value="deleteBrand")
    public ResultVO deleteBrand(@RequestBody BrandDeleteReq req) {
        log.info("GoodsBrandController.deleteBrand req={}", req);
        BrandOperateResp resp = goodsBrandService.deleteBrand(req);
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success();
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }
}
