package com.iwhalecloud.retail.oms.dto.response.gift;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserGiftExchangeRespDTO implements Serializable{

  private static final long serialVersionUID = -3773999699538556671L;

  private Integer userId;
  private Integer giftId;
  private Date createDate;
  private String giftName;
  private Integer needPointAmount;
  private Integer giftType;
  private Integer gainAmount;
  private Integer totalPoint;

}
