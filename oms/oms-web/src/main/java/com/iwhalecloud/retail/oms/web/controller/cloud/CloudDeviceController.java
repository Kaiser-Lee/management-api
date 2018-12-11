package com.iwhalecloud.retail.oms.web.controller.cloud;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.CloudDeviceDTO;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudDevicePageReq;
import com.iwhalecloud.retail.oms.service.CloudDeviceService;
import com.iwhalecloud.retail.oms.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.oms.web.controller.BaseController;
import com.iwhalecloud.retail.oms.web.interceptor.UserContext;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import com.iwhalecloud.retail.system.service.RegionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.resp.RegionsGetResp;

import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/17 16:28
 * @Description: 云货架终端设备（增删改查）
 */

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cloudDevice")
public class CloudDeviceController extends BaseController {

    @Reference
    private CloudDeviceService cloudDeviceService;

    @Reference
    private PartnerShopService partnerShopService;

    @Reference
    private RegionService regionService;

    @ApiOperation(value = "查询CloudDeviceDTO列表", notes = "根据adscriptionCity adscriptionShop id deviceName status onlineType deviceType，查询CloudDeviceDTO对象")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //查询云货架终端设备列表
    @RequestMapping(value = "/queryCloudDeviceList", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Page<CloudDeviceDTO>> queryCloudDeviceList(@RequestBody @ApiParam(value = "查询条件", required = true) CloudDevicePageReq request) {
        ResultVO<Page<CloudDeviceDTO>> resultVO = new ResultVO<>();
        String deviceName = request.getDeviceName();
        String num = request.getNum();
        if (deviceName != null) {
            request.setDeviceName("%" + deviceName + "%");
        }
        if (num != null) {
            request.setNum("%" + num + "%");
        }
        try {
            Page<CloudDeviceDTO> cloudDeviceDTO = cloudDeviceService.queryCloudDeviceList(request);
            for (int i = 0; i < cloudDeviceDTO.getRecords().size(); i++) {
                String adscriptionShop = cloudDeviceDTO.getRecords().get(i).getAdscriptionShop();
                if (!StringUtils.isEmpty(adscriptionShop)) {
                    PartnerShopDTO request1 = new PartnerShopDTO();
                    request1.setPartnerId(adscriptionShop);
                    PartnerShopDTO dto = partnerShopService.getPartnerShop(request1);
                    if (dto != null) {
                        cloudDeviceDTO.getRecords().get(i).setAdscriptionShopName(dto.getName());
                    }
                }
                String adscriptionCity = cloudDeviceDTO.getRecords().get(i).getAdscriptionCity();
                RegionsGetReq req = new RegionsGetReq();
                req.setRegion_id(adscriptionCity);
                RegionsGetResp resRegionsGetResp = regionService.getRegion(req);
                if (resRegionsGetResp != null) {
                    if (resRegionsGetResp.getRegions() != null) {
                        String localName = resRegionsGetResp.getRegions().getLocal_name();
                        cloudDeviceDTO.getRecords().get(i).setAdscriptionCityName(localName);
                    }

                }
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(cloudDeviceDTO);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "查询CloudDeviceDTO详情", notes = "根据id，查询CloudDeviceDTO对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "设备编码", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //查询云货架终端设备详情
    @RequestMapping(value = "/queryCloudDeviceListDetails", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO<CloudDeviceDTO> queryCloudDeviceListDetails(@PathVariable String num) {
        ResultVO resultVO = new ResultVO();
        try {
            List<CloudDeviceDTO> cloudDeviceDTOs = cloudDeviceService.queryCloudDeviceListDetails(num);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(cloudDeviceDTOs.get(0));
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "新增CloudDeviceDTO", notes = "传入CloudDeviceDTO对象，进行保存操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //云货架终端设备新增
    @RequestMapping(value = "/createCloudDevice", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO createCloudDevice(@RequestBody @ApiParam(value = "CloudDeviceDTO", required = true) CloudDeviceDTO request) {
        ResultVO resultVO = new ResultVO();
        try {
            String userId = UserContext.getUserId();
            request.setCreator(userId);
            String num = request.getNum();
            List<CloudDeviceDTO> dto = cloudDeviceService.queryCloudDeviceListDetails(num);
            if (dto != null && dto.size() > 0) {
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("设备编码已存在，新增失败");
            } else {
                cloudDeviceService.createCloudDevice(request);
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData("新增成功");
                resultVO.setResultMsg("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "修改CloudDeviceDTO", notes = "接口支持根据num修改终端设备")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //云货架终端设备修改
    @RequestMapping(value = "/editCloudDevice", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO updateCloudDeviceStatus(@RequestBody CloudDeviceDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            String userId = UserContext.getUserId();
            dto.setModifier(userId);
            cloudDeviceService.editCloudDevice(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("修改成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "删除CloudDeviceDTO", notes = "根据num，进行更新是否删除字段为1")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    //云货架终端设备删除
    @RequestMapping(value = "/deleteCloudDevice", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO deleteCloudDevice(@RequestBody CloudDeviceDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            String num = dto.getNum();
            List<CloudDeviceDTO> cloudDeviceDTO = cloudDeviceService.queryCloudDeviceListDetails(num);
            if (cloudDeviceDTO != null && cloudDeviceDTO.size() > 0) {
                int status = cloudDeviceDTO.get(0).getStatus();
                if (status == 20) {
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("已启用的设备无法删除");
                } else {
                    cloudDeviceService.deleteCloudDevice(dto);
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                    resultVO.setResultData("删除成功");
                    resultVO.setResultMsg("success");
                }
            } else {
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("查询不到该条设备的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }
}

