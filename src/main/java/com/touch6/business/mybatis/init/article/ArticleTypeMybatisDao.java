package com.touch6.business.mybatis.init.article;


import com.touch6.business.entity.init.article.ArticleType;
import com.touch6.business.mybatis.common.MyBatisRepository;

import java.util.List;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface ArticleTypeMybatisDao {
    List<ArticleType> findTypes();
}
