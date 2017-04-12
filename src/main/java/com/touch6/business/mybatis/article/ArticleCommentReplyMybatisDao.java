package com.touch6.business.mybatis.article;


import com.touch6.business.entity.article.Article;
import com.touch6.business.entity.article.ArticleCommentReply;
import com.touch6.business.mybatis.common.MyBatisRepository;

import java.util.List;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface ArticleCommentReplyMybatisDao {
    int addReply(ArticleCommentReply commentReply);

    ArticleCommentReply findById(String replyId);

    List<ArticleCommentReply> replyList(String commentId);

}
