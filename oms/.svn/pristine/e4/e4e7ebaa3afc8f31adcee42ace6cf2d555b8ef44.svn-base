package com.iwhalecloud.retail.oms.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.GoodsCountRankConstants;
import com.iwhalecloud.retail.oms.consts.ReportOpeDataConstants;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.response.report.ReportOperationDeskBottomRespDTO;
import com.iwhalecloud.retail.oms.dto.response.report.ReportOperationDeskTopRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.report.ReportOperationDeskBottomReqDTO;
import com.iwhalecloud.retail.oms.service.EventOperationDeskService;
import com.iwhalecloud.retail.oms.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.oms.web.interceptor.UserContext;
import com.iwhalecloud.retail.order.service.OrderOperationDeskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportOperationDesk")
public class ReportOperationDeskController {

    @Reference
    private OrderOperationDeskService orderOperationDeskService;

    @Reference
    private EventOperationDeskService eventOperationDeskService;

    @ApiOperation(value = "查询运营人员工作台上部展示数据")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数未填对"),@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @RequestMapping(value = "/topPartData", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO<ReportOperationDeskTopRespDTO> getTopPartData(){
        ResultVO<ReportOperationDeskTopRespDTO> resultVO = new ResultVO<>();
        ReportOperationDeskTopRespDTO respDTO = genTopPartRespDTO();
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(respDTO);
        resultVO.setResultMsg("SUCCESS");
        return resultVO;
    }

    @ApiOperation(value = "查询运营人员工作台下部展示数据")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数未填对"),@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @RequestMapping(value = "/bottomPartData", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<ReportOperationDeskBottomRespDTO> getBottomPartData(@RequestBody ReportOperationDeskBottomReqDTO request)throws Exception {
        ResultVO<ReportOperationDeskBottomRespDTO> resultVO = new ResultVO<>();
        ReportOperationDeskBottomRespDTO respDTO = genBottomPartRespDTO(request);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(respDTO);
        resultVO.setResultMsg("SUCCESS");
        return resultVO;
    }

    private ReportOperationDeskBottomRespDTO genBottomPartRespDTO(ReportOperationDeskBottomReqDTO request)throws Exception{
        ReportOperationDeskBottomRespDTO respDTO = new ReportOperationDeskBottomRespDTO();
        // 通过用户获取其归属区域
        String areaCode = UserContext.getAdminUser().getCity_id();
        //String areaCode = null;
        String type = request.getReportType();
        if (null != type) {
            List<Map<String,Object>> shopRankList = null;
            List<Map<String,Object>> reportDataList = null;
            if (type.equals(ReportOpeDataConstants.TYPE_SALE_AMOUNT)){
                shopRankList = orderOperationDeskService.getShopSaleAmountRankByArea(areaCode,request.getBeginTime(),request.getEndTime());
                reportDataList = orderOperationDeskService.getTimeIntervalAmountByArea(areaCode,request.getBeginTime(),request.getEndTime());
            } else if (type.equals(ReportOpeDataConstants.TYPE_VISIT_VOLUME)){
                // 获取访问量
                shopRankList = eventOperationDeskService.getShopVisitVolumeRankByArea(areaCode,request.getBeginTime(),request.getEndTime());
                reportDataList = eventOperationDeskService.getTimeIntervalVisitByArea(areaCode,request.getBeginTime(),request.getEndTime());
            } else if (type.equals(ReportOpeDataConstants.TYPE_WORK_TIME)){
                shopRankList = eventOperationDeskService.getShopWorkTimeRankByArea(areaCode,request.getBeginTime(),request.getEndTime());
                //根据日期、区域查询云货架工作时长
                reportDataList = eventOperationDeskService.getOnOffLineTimeAboutDevice(areaCode,request.getBeginTime(),request.getEndTime());
            }
            respDTO.setShopRankList(shopRankList);
            respDTO.setReportDataList(reportDataList);
        }
        return respDTO;
    }

    //ReportOperationDeskTopReqDTO request
    private ReportOperationDeskTopRespDTO genTopPartRespDTO(){
        ReportOperationDeskTopRespDTO respDTO = new ReportOperationDeskTopRespDTO();
        // 通过用户获取其归属区域
        String areaCode = UserContext.getAdminUser().getCity_id();
        //String areaCode = null;
        // 订单量
        respDTO.setOrderCount(orderOperationDeskService.getTodayOrderCountByArea(areaCode));
        // 销售额
        respDTO.setOrderAmount(orderOperationDeskService.getTodayOrderAmountByArea(areaCode));
        // 加购数
        int cartEventCount = eventOperationDeskService.getTodayEventCountByArea(areaCode, GoodsCountRankConstants.GOODS_CART_EVENT);
        respDTO.setCartCount(cartEventCount);
        // 成交量
        int saleCount = orderOperationDeskService.getTodaySaleCountByArea(areaCode);
        int clickCount = eventOperationDeskService.getTodayEventCountByArea(areaCode, GoodsCountRankConstants.GOODS_CLICK_EVENT);
        double transRate = 0;
        if (0 != clickCount){
            transRate = (saleCount/clickCount)*100;
        }
        // 转化率
        String transRateStr = new BigDecimal(transRate).setScale(1,BigDecimal.ROUND_HALF_UP).toString() + "%";
        respDTO.setTransRate(transRateStr);
        return respDTO;
    }


}
