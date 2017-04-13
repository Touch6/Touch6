package com.touch6.business.api.service;

import com.touch6.business.dto.article.ArticleCommentDto;

import java.util.List;

/**
 * Created by xuan.touch6@qq.com on 2017/4/12.
 */
public interface ArticleCommentService {
    ArticleCommentDto launchComment(ArticleCommentDto commentDto);

    List<ArticleCommentDto> commentList(String articleId);
}
