package com.iwhalecloud.retail.oms.dto.resquest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/7 10:30
 * @Description:
 */

@Data
@ApiModel(value = "对应查询请求类")
public class EventInteractionTimeReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属厅店ID")
    private String adscriptionShopId; //所属厅店ID

    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity; //所属城市

    @ApiModelProperty(value = "所属城区")
    private String adscriptionCityArea; //所属城区

    @ApiModelProperty(value = "开始日期")
    private String startTime; //开始日期

    @ApiModelProperty(value = "结束日期")
    private String endTime; //结束日期
}

