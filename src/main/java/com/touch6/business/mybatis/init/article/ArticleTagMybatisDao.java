package com.touch6.business.mybatis.init.article;


import com.touch6.business.entity.init.article.ArticleTag;
import com.touch6.business.mybatis.common.MyBatisRepository;

import java.util.List;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface ArticleTagMybatisDao {
    int addArticleTag(List<ArticleTag> articleTags);

    List<ArticleTag> findTagListByArticleIds(List<String> articleIds);

    int deleteTagsByArticleId(String id);
}
