package com.iwhalecloud.retail.oms.web.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.web.annotation.PassToken;
import com.iwhalecloud.retail.oms.web.utils.ResponseComUtil;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.pay.dto.request.NotifyRequestDTO;
import com.iwhalecloud.retail.pay.dto.request.PayParamsRequestDTO;
import com.iwhalecloud.retail.pay.service.PayManagerOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/pay")
public class PayController {


    @Reference
    private PayManagerOpenService payManagerOpenService;


    /**
     * 去支付
     *
     * @return
     */
    @PostMapping("/toPay")
    @PassToken
    public ResultVO toPay(@RequestBody PayParamsRequestDTO toPayRequest) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = payManagerOpenService.toPay(toPayRequest);
        ResponseComUtil.orderRespToResultVO(resp, resultVO);
        return resultVO;
    }

    @PostMapping("/notifyUrl")
    @PassToken
    public ResultVO notify(@RequestBody NotifyRequestDTO notifyRequest) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = payManagerOpenService.notify(notifyRequest);
        ResponseComUtil.orderRespToResultVO(resp, resultVO);
        return resultVO;
    }

}
