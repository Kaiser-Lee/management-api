package com.iwhalecloud.retail.oms.web.controller.rop;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.RegionsDTO;
import com.iwhalecloud.retail.system.service.RegionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsListResp;

/**
 * @Author My
 * @Date 2018/10/11
 **/
@RestController
@RequestMapping("/api/rop/region")
@Slf4j
public class RegionROPController {

	@Reference
    private RegionService regionService;

    /**
     * 查询省、市、区列表
     * @param regionType
     * @param regionParentId
     * @return
     */
    @GetMapping(value="listRegions")
    @ApiOperation(value = "查询省、市、区列表", notes = "查询省、市、区列表，可根据父级获取子级列表或者查询省级区域。")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "regionType", value = "区域类型，参数值为0则会忽略regionParentId参数查询省级列表，为1时根据区域父级查询列表", paramType = "query", required = true, dataType = "String"),
        @ApiImplicitParam(name = "regionParentId", value = "区域父级ID，如果不传则默认为湖南省", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数不全"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    public ResultVO<RegionsListResp> listRegions(@RequestParam(value = "regionType", required = true) String regionType, 
    		@RequestParam(value = "regionParentId", required = false) String regionParentId) {
        log.info("RegionController listRegions regionType={} , regionParentId={} ", regionType, regionParentId);
        RegionsListReq req = new RegionsListReq();
        if(StringUtils.isNotEmpty(regionType)){
            req.setRegion_type(regionType);
        }
        if(StringUtils.isNotEmpty(regionParentId)){
            req.setRegion_parent_id(regionParentId);
        } else {
        	req.setRegion_parent_id("430000"); //默认查询湖南
        }
        RegionsListResp resp = regionService.listRegions(req);
        if (org.springframework.util.StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
            return ResultVO.success(resp);
        } else {
            return ResultVO.error(resp.getError_code(),resp.getError_msg());
        }
    }

    @ApiOperation(value = "查询区域父级ID", notes = "传入区域ID查询父级ID")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getPregionsId", method = RequestMethod.GET)
    public ResultVO<RegionsDTO> getPregionsId(@RequestParam String regionsId){
        if(StringUtils.isEmpty(regionsId)){
            return ResultVO.error("regionsId must be not null");
        }

        RegionsDTO regionsDTO = regionService.getPregionId(regionsId);
        if(null == regionsDTO){
            log.error("regionsDTO is null");
            return ResultVO.error("找不到本地网 参数请传区级ID");
        }
        return ResultVO.success(regionsDTO);
    }

}
