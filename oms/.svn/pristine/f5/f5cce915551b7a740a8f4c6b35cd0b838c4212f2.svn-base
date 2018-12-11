package com.iwhalecloud.retail.oms.web.controller.rop;

import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.service.dubbo.rop.GoodsTagsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zte.params.tags.resp.TagListResp;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author mzl
 * @date 2018/10/26
 */
@RestController
@RequestMapping("/api/rop/GoodsTags")
public class GoodsTagsROPController {

	@Reference
    private GoodsTagsService goodsTagsService;

    @GetMapping(value="listTag")
    public ResultVO listTag() {
        TagListResp resp = goodsTagsService.listTag();
        if (StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp.getTagList());
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }
}
