package com.iwhalecloud.retail.oms.dto.resquest.gift;

import java.io.Serializable;

import lombok.Data;

/**
 * @author he.sw
 * @date 2018/10/16
 * * @Description: 用户积分流水集传送对象
 */
@Data
public class ListUserGiftExchangeDTO implements Serializable {

    private static final long serialVersionUID = -3903559699538556671L;

    private Long userId;
    private Integer pageSize;
	private Integer pageNo;
	private Integer pageOffset;
	private Integer pageStartset;


}
