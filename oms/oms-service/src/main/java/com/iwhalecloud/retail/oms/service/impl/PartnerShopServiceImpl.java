package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.PartnerShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import params.req.PartnerShopPageReq;
import params.resp.PartnerShopPageResp;

/**
 * @Author My
 * @Date 2018/10/17
 **/
@Slf4j
@Service
public class PartnerShopServiceImpl implements PartnerShopService {
    @Autowired
    private ZteRopClient client;

    @Override
    public ResultVO<PartnerShopPageResp> qryPartnerShopPageByParam(PartnerShopPageReq req) {
        log.info("PartnerShopServiceImpl qryPartnerShopPageByParam req={} ",req);
        PartnerShopPageResp resp = null;
        try {
            resp = client.execute(req, PartnerShopPageResp.class);
            ResultVO.success();
            if(null == resp){
                return ResultVO.error("resp is null");
            }
        }catch (Exception e){
            log.error("PartnerShopServiceImpl qryPartnerShopPageByParam  Exception={} ",e);
            ResultVO.error();
        }
        return ResultVO.success(resp);
    }

}
