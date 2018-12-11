package com.iwhalecloud.retail.oms.dto.resquest;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Auther: lin.wh
 * @Date: 2018/10/31 22:31
 * @Description:
 */

@Data
@ApiModel(value = "云货架分页查询请求参数对象")
public class CloudShelfPageReq extends PageVO {

    @ApiModelProperty(value="用户ID(可从session获取)")
    private String userId;

    @ApiModelProperty(value = "厅店id(可从session获取)")
    private String adscriptionShopId;

    @ApiModelProperty(value = "货架编号、名称")
    private String param;
}

