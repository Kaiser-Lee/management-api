package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.ztesoft.net.app.base.core.model.MemberAddress;
import zte.params.addr.req.MemberAddressAddReq;
import zte.params.addr.req.MemberAddressDeleteReq;
import zte.params.addr.req.MemberAddressEditReq;
import zte.params.addr.req.MemberAddressListReq;
import zte.params.addr.resp.MemberAddressDeleteResp;

import java.util.List;
/**
 * @author pjq
 * @date 2018/10/8
 */
public interface MemberAddressService {
    /**
     *查询用户常用地址
     * @param req
     * @return
     */
    ResultVO<List<MemberAddress>> listMemberAddress(MemberAddressListReq req);

    /**
     *添加会员常用地址
     * @param req
     * @return
     */
    ResultVO<MemberAddress> addMemeberAddress(MemberAddressAddReq req);

    /**
     *修改用户常用地址信息
     * @param req
     * @return
     */
    ResultVO editMemberAddress(MemberAddressEditReq req);

    /**
     *删除常用地址
     * @param req
     * @return
     */
    ResultVO<MemberAddressDeleteResp> deleteMemberAddress(MemberAddressDeleteReq req);
}
