package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.iwhalecloud.retail.oms.rop.ZteRopClient;
import com.iwhalecloud.retail.oms.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import params.req.PartnerByIdReq;
import params.req.PartnerShopListReq;
import params.resp.PartnerByIdResp;
import params.resp.PartnerShopListResp;

/**
 * @Author My
 * @Date 2018/10/17
 **/
@Slf4j
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ZteRopClient client;

    @Override
    public ResultVO<PartnerShopListResp> listShop(PartnerShopListReq req) {
        log.info("ShopServiceImpl listShop req={} ",req);
        PartnerShopListResp resp = null;
        try {
             resp = client.execute(req, PartnerShopListResp.class);
             if(null == resp){
                 return ResultVO.error("resp  is null");
             }
            if (ResultCodeEnum.SUCCESS.getCode().equals(resp.getError_code())) {
                return ResultVO.success(resp);
            } else {
                return ResultVO.error(resp.getError_code(),resp.getError_msg());
            }
        }catch (Exception e){
            log.error("ShopServiceImpl listShop Exception={} ",e);
            ResultVO.error();
        }
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<PartnerByIdResp> getShopById(PartnerByIdReq req) {
        log.info("ShopServiceImpl getShopById req={} ",req);
        PartnerByIdResp resp = null;
        try {
            resp = client.execute(req, PartnerByIdResp.class);
            if(null == resp){
                return ResultVO.error("resp is null");
            }
        }catch (Exception e){
            log.error("ShopServiceImpl getShopById Exception={} ",e);
            ResultVO.error();
        }
        return ResultVO.success(resp);
    }
}
