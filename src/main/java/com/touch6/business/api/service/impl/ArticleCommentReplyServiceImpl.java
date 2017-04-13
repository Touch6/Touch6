package com.touch6.business.api.service.impl;

import com.touch6.business.api.service.ArticleCommentReplyService;
import com.touch6.business.dto.article.ArticleCommentReplyDto;
import com.touch6.business.entity.article.ArticleCommentReply;
import com.touch6.business.mybatis.article.ArticleCommentMybatisDao;
import com.touch6.business.mybatis.article.ArticleCommentReplyMybatisDao;
import com.touch6.business.mybatis.article.ArticleMybatisDao;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.error.constant.SystemErrorConstant;
import com.touch6.utils.T6StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.mapper.BeanMapper;

import java.util.Date;
import java.util.List;

/**
 * Created by xuan.touch6@qq.com on 2017/4/12.
 */
@SuppressWarnings("ALL")
@Service
public class ArticleCommentReplyServiceImpl implements ArticleCommentReplyService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleCommentReplyServiceImpl.class);

    @Autowired
    private ArticleCommentReplyMybatisDao articleCommentReplyMybatisDao;
    @Autowired
    private ArticleCommentMybatisDao articleCommentMybatisDao;
    @Autowired
    private ArticleMybatisDao articleMybatisDao;

    @Override
    @Transactional
    public ArticleCommentReplyDto addReply(ArticleCommentReplyDto replyDto) {
        ArticleCommentReply reply = BeanMapper.map(replyDto, ArticleCommentReply.class);
        //文章follows是否递增
        int isfollower = articleCommentMybatisDao.isFollower(reply.getSponsorId());
        if (isfollower == 0) {
            //文章follows递增
            int followsIncrease = articleCommentMybatisDao.increaseFollows(reply.getCommentId());
            if (followsIncrease != 1) {
                throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
            }
            logger.info("文章follows递增:[{}]", followsIncrease);
        }
        //不递增
        //生成id
        reply.setId(T6StringUtils.generate32uuid());
        //设置时间
        Date time = new Date();
        reply.setCreateTime(time);
        reply.setUpdateTime(time);
        int inserted = articleCommentReplyMybatisDao.addReply(reply);
        if (inserted != 1) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
        logger.info("添加评论:[{}]回复成功", reply.getCommentId());
        return BeanMapper.map(reply, ArticleCommentReplyDto.class);
    }

    @Override
    public List<ArticleCommentReplyDto> replyList(String commentId) {
        List<ArticleCommentReply> replies = articleCommentReplyMybatisDao.replyList(commentId);
        return BeanMapper.mapList(replies, ArticleCommentReplyDto.class);
    }
}
