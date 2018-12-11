package com.iwhalecloud.retail.oms.dto.resquest.gift;

import java.io.Serializable;

import lombok.Data;

/**
 * @author hesw
 * @date 2018/10/26
 * @Description: 查询奖品传送对象
 */
@Data
public class ListGiftDTO implements Serializable {

    private static final long serialVersionUID = -3903559699538996671L;

    private Integer giftType;
    private Integer pageSize;
	private Integer pageNo;
	private Integer pageOffset;
	private Integer pageStartset;


}
