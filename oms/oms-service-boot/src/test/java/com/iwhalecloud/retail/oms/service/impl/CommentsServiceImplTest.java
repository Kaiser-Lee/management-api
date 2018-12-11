package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.OmsServiceApplication;
import com.iwhalecloud.retail.oms.service.CommentsService;
import com.ztesoft.net.mall.core.model.Comments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import params.member.req.CommentAddReq;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsServiceApplication.class)
public class CommentsServiceImplTest {
    @Autowired
    private CommentsService commentsService;
    @Test
    public void addComments() {
        CommentAddReq req = new CommentAddReq();
        Comments comments = new Comments();
        comments.setObject_id("2018110614435679000379114");
        comments.setOrder_id("2018110614435679000379114");
        comments.setObject_type("1");
        comments.setQuotas("就是这样测试的");
        req.setComment(comments);
        req.setAction("1");
        req.setMember_id("181031588100419686");
        //commentsService.addComments(req);
    }
}
