package com.iwhalecloud.retail.oms.web.controller;

import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ResultVO;

/**
 * @author Z
 * @date 2018/10/8
 */
public class BaseController<T> {

    /**
     * 返回处理成功对象
     * @param t 结果数据对象
     * @return
     */
    public ResultVO<T> successResultVO(T t) {
        return resultVO(OmsCommonConsts.RESULE_CODE_SUCCESS,"处理成功",t);
    }

    /**
     * 返回处理失败对象
     * @return
     */
    public ResultVO<T> failResultVO(){
        return resultVO(OmsCommonConsts.RESULE_CODE_FAIL,"处理失败",null);
    }

    /**
     * 返回处理失败对象
     * @return
     */
    public ResultVO<T> failResultVO(String errorMsg){
        return resultVO(OmsCommonConsts.RESULE_CODE_FAIL,errorMsg,null);
    }


    /**
     *
     * @param resultCode 结果编码
     * @param resultMsg 结果消息
     * @param t 结果数据对象
     * @return
     */
    public ResultVO<T> resultVO(String resultCode,String resultMsg,T t) {
        ResultVO resultVO = new ResultVO();

        resultVO.setResultCode(resultCode);
        resultVO.setResultMsg(resultMsg);
        resultVO.setResultData(t);
        return resultVO;
    }
}
