package com.touch6.business.api.service;

import com.touch6.business.dto.article.ArticleDto;

/**
 * Created by PAX on 2017/4/7.
 */
public interface ArticleService {
    /**
     * 用户撰写文章
     *
     * @param uid
     * @param articleDto
     * @return
     */
    ArticleDto writeArticle(String uid, ArticleDto articleDto);
}
