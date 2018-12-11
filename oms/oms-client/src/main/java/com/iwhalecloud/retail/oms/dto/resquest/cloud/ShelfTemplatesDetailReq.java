package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Ji.kai
 * @Date: 2018/11/14 11:00
 * @Description: 云货架末模板新增修改请求对象
 */
@Data
@ApiModel(value = "云货架模板详情对象请求参数")
public class ShelfTemplatesDetailReq implements Serializable {

    //属性 begin
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private java.lang.Long id;

    /**
     * defCategoryId
     */
    @ApiModelProperty(value = "defCategoryId")
    private java.lang.String defCategoryId;

    /**
     * operatingPositionId
     */
    @ApiModelProperty(value = "operatingPositionId")
    private java.lang.String operatingPositionId;

    /**
     * operatingPositionId
     */
    @ApiModelProperty(value = "操作动作 add：新增 delete：删除")
    private java.lang.String action;
}
