package com.touch6.business.mybatis.article;


import com.touch6.business.entity.article.Article;
import com.touch6.business.entity.article.ArticleComment;
import com.touch6.business.mybatis.common.MyBatisRepository;

import java.util.List;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface ArticleCommentMybatisDao {
    int launchComment(ArticleComment comment);

    ArticleComment findById(String commentId);

    List<ArticleComment> commentList(String articleId);

    int findCommentNumbers(String articleId);

    List<String> findCommentSponsors(String articleId);

    int isFollower(String sponsorId);

    int increaseFollows(String commentId);

    void increaseApprovalAmount(String objectId);

    void increaseOpposeAmount(String articleId);

    int findApprovalAmountById(String objectId);

    int findOpposeAmountById(String objectId);
}
