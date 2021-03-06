package com.iwhalecloud.retail.oms.web.controller.gift;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.UserGiftExchangeDTO;
import com.iwhalecloud.retail.oms.service.gift.GiftService;
import com.iwhalecloud.retail.oms.service.gift.UserGiftExchangeService;
import com.iwhalecloud.retail.oms.service.gift.UserPointRecordService;
import com.iwhalecloud.retail.oms.web.controller.BaseController;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 个人中心-会员积分
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/gift")
public class GiftController  extends BaseController {

    @Reference
    private GiftService giftService;
    
    @Reference
    private UserGiftExchangeService userGiftExchangeService;
    
    @Reference
    private UserPointRecordService userPointRecordService;

    
    @ApiOperation(value = "查看积分", notes = "传入userId，进行保存操作")
    @ApiImplicitParam(name = "userId", value = "userId", paramType = "query", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/getUserPoint")
    public ResultVO findByUserId(@RequestParam Long userId) {
        ResultVO resultVO = new ResultVO();
        try {
        	if(null == userId){
                return failResultVO();
            }
            UserPointRecordRespDTO resp = userPointRecordService.getUserPoint(userId);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(resp);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
        	log.error("查看用户积分异常",e);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }
    
    @ApiOperation(value = "兑换奖品", notes = "传入TUserGiftExchangeDTO对象，进行保存操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/saveUserGiftExchange", method = RequestMethod.POST)
    public ResultVO saveUserGiftExchange(@RequestBody UserGiftExchangeDTO request) {
        ResultVO resultVO = new ResultVO();
        try {
        	Integer addGift = userGiftExchangeService.saveUserGiftExchange(request);
            if(null == addGift || addGift < 1){
                return failResultVO();
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            log.error("兑换奖品异常",e);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }
    
    @ApiOperation(value = "查看用户的奖品兑换记录", notes = "传入userId，进行保存操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", required = true, dataType = "Integer")
     })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listUserExchangeRecord")
    public ResultVO listUserExchangeRecord(@RequestParam Long userId,
    		@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        ResultVO resultVO = new ResultVO();
        try {
        	if(null == userId){
                return failResultVO();
            }
        	if(null == pageNo){
                return failResultVO();
            }
        	// 防止页大小过大导致查询缓慢
        	if(null == pageSize || 1000 < pageSize){
                return failResultVO();
            }
        	ListUserGiftExchangeDTO request = new ListUserGiftExchangeDTO();
        	request.setUserId(userId);
        	request.setPageNo(pageNo);
            request.setPageSize(pageSize);
            List<UserGiftExchangeRespDTO> resp = userGiftExchangeService.listUserPointRecord(request);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(resp);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            log.error("查看用户的奖品兑换记录异常",e);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }
    
    
    @ApiOperation(value = "查看奖品", notes = "传入GiftDTO对象，进行保存操作")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "giftType", value = "giftType", paramType = "query", required = true, dataType = "Integer"),
        @ApiImplicitParam(name = "pageNo", value = "pageNo", paramType = "query", required = true, dataType = "Integer"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", required = true, dataType = "Integer")
     })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listGift")
    public ResultVO listGift(@RequestParam Integer giftType,
    		@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        ResultVO resultVO = new ResultVO();
        ListGiftDTO request = new ListGiftDTO();
        if(null == giftType){
            return failResultVO();
        }
    	if(null == pageNo){
            return failResultVO();
        }
    	// 防止页大小过大导致查询缓慢
    	if(null == pageSize || 1000 < pageSize){
            return failResultVO();
        }
        request.setGiftType(giftType);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        try {
            List<GiftRespDTO> resp = giftService.listGift(request);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(resp);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
        	log.error("查看奖品异常",e);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }

    
}
