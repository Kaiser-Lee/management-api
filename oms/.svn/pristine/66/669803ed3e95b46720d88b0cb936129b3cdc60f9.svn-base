package com.iwhalecloud.retail.oms.dto.resquest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Comments;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/10/10
 **/
@Data
@ApiModel(value = "添加评论对象")
public class CommentsRequestDTO implements Serializable {
    @ApiModelProperty(value = "评论对象")
    private List<Comments> comments;
    @ZteSoftCommentAnnotationParam(
            name = "类型",
            type = "String",
            isNecessary = "Y",
            desc = "类型：1商品 2订单 默认为1"
    )
    private String action = "1";
}
