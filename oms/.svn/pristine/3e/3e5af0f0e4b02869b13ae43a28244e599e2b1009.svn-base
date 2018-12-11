package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.ResultVO;
import com.ztesoft.net.framework.database.Page;
import params.member.req.CommentAddReq;
import zte.params.member.req.CommentPageListReq;

import java.util.List;

/**
 * @Author pjq
 * @Date 2018/10/10
 **/
public interface CommentsService {
    /**
     * 添加评论
     * @param req
     */
    ResultVO<Boolean> addComments(List<CommentAddReq> req);

    /**
     *分页查询用户评论
     * @param req
     * @return
     */
    ResultVO<Page> queryCommoentForPage(CommentPageListReq req);
}
