package com.iwhalecloud.retail.oms.service;

public interface TokenService {

    /**
     * 通过opendId获取jwt的token
     * @param memberId
     * @param memberSessionId
     * @return
     */
    String getMemberToken(String memberId, String memberSessionId);

    /**
     *  通过userId获取jwt的token
     * @param memberSessionId
     * @return
     */
    String getUserToken(String userId, String memberSessionId);
}
