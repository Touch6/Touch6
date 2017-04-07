package com.touch6.business.mybatis;


import com.touch6.business.entity.User;
import com.touch6.business.entity.article.Article;
import com.touch6.business.mybatis.common.MyBatisRepository;

import java.util.Map;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface ArticleMybatisDao {
    int writeArticle(Article article);
}
