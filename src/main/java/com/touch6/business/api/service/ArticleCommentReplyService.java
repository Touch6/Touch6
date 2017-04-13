package com.touch6.business.api.service;

import com.touch6.business.dto.article.ArticleCommentReplyDto;

import java.util.List;

/**
 * Created by PAX on 2017/4/12.
 */
public interface ArticleCommentReplyService {
    ArticleCommentReplyDto addReply(ArticleCommentReplyDto replyDto);

    List<ArticleCommentReplyDto> replyList(String commentId);
}
