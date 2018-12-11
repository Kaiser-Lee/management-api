package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.ResultVO;
import params.req.PartnerByIdReq;
import params.req.PartnerShopListReq;
import params.resp.PartnerByIdResp;
import params.resp.PartnerShopListResp;

/**
 * @Author pjq
 * @Date 2018/10/11
 **/
public interface ShopService {
    /**
     * 获取店铺列表
     * @param req
     * @return
     */
    ResultVO<PartnerShopListResp> listShop(PartnerShopListReq req);

    /**
     *获取分销商信息(包括店铺信息)
     * @param req
     * @return
     */
    ResultVO<PartnerByIdResp> getShopById(PartnerByIdReq req);

}
