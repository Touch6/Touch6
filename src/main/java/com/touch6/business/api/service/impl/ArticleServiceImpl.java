package com.touch6.business.api.service.impl;

import com.touch6.business.api.service.ArticleService;
import com.touch6.business.dto.article.ArticleDto;
import com.touch6.business.entity.User;
import com.touch6.business.entity.article.Article;
import com.touch6.business.entity.article.ArticleTag;
import com.touch6.business.mybatis.ArticleMybatisDao;
import com.touch6.business.mybatis.ArticleTagMybatisDao;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.error.constant.CommonErrorConstant;
import com.touch6.utils.T6StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.mapper.BeanMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by PAX on 2017/4/7.
 */
@SuppressWarnings("ALL")
@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    private UserMybatisDao userMybatisDao;
    @Autowired
    private ArticleTagMybatisDao articleTagMybatisDao;
    @Autowired
    private ArticleMybatisDao articleMybatisDao;

    @Override
    @Transactional
    public ArticleDto writeArticle(String uid, ArticleDto articleDto) {
        //校验dto属性
        User author = userMybatisDao.findByUid(uid);
        if (author == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Article article = BeanMapper.map(articleDto, Article.class);
        //articleId为空新增，不为空修改
        if (StringUtils.isBlank(article.getId())) {
            article.setId(T6StringUtils.generate32uuid());
            //插入文章标签
            if (StringUtils.isNotBlank(article.getTag())) {
                String[] tags = StringUtils.split(article.getTag(), ",");
                List<ArticleTag> tagList = new ArrayList<>();
                for (String tag : tags) {
                    ArticleTag at = new ArticleTag();
                    at.setArticleId(article.getId());
                    at.setName(tag);
                    tagList.add(at);
                }
                int tagcount = articleTagMybatisDao.addArticleTag(tagList);
                logger.info("文章:[{}]插入[{}]个标签", article.getId() + "/" + article.getTitle(), tagcount);
            }
            Date date = new Date();
            article.setUid(author.getUid());
            article.setAuthor(author.getName());
            article.setCreateTime(date);
            article.setUpdateTime(date);
            article.setAuditStatus(0);
            int inserted = articleMybatisDao.writeArticle(article);
            logger.info("插入文章数:[{}]", inserted);
            return BeanMapper.map(article, ArticleDto.class);
        } else {
            //修改
            //查询出原文章
            Article old = articleMybatisDao.findById(article.getId());
            //删除原来标签
            int d = articleTagMybatisDao.deleteTagsByArticleId(article.getId());
            logger.info("修改文章，删除原来标签个数:[{}]", d);
            //插入文章标签
            if (StringUtils.isNotBlank(article.getTag())) {
                String[] tags = StringUtils.split(article.getTag(), ",");
                List<ArticleTag> tagList = new ArrayList<>();
                for (String tag : tags) {
                    ArticleTag at = new ArticleTag();
                    at.setArticleId(article.getId());
                    at.setName(tag);
                    tagList.add(at);
                }
                int tagcount = articleTagMybatisDao.addArticleTag(tagList);
                logger.info("修改文章:[{}]插入[{}]个标签", article.getId() + "/" + article.getTitle(), tagcount);
            }
            Date date = new Date();
            article.setUid(old.getUid());
            article.setAuthor(old.getAuthor());
            article.setCreateTime(old.getCreateTime());
            article.setUpdateTime(date);
            article.setAuditStatus(0);
            int updated = articleMybatisDao.updateArticle(article);
            logger.info("修改文章数:[{}]", updated);
            return BeanMapper.map(article, ArticleDto.class);
        }
    }
}
