package com.touch6.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.ArticleCommentService;
import com.touch6.business.api.service.ArticleService;
import com.touch6.business.dto.article.ArticleCommentDto;
import com.touch6.business.dto.article.ArticleDto;
import com.touch6.business.entity.init.article.ArticleCategory;
import com.touch6.business.entity.init.article.ArticleType;
import com.touch6.commons.PageObject;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/api/v1/article/comment")
public class ArticleCommentController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleCommentController.class);

    @Autowired
    private ArticleCommentService articleCommentService;

    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity launchNewComment(@RequestBody ArticleCommentDto commentDto) {
        try {
            logger.info("接收到新的文章评论:[{}]", JSONObject.toJSONString(commentDto));
            ArticleCommentDto comment = articleCommentService.launchComment(commentDto);
            Success ok = new Success(200, comment, "评论成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity commentList(@RequestParam("articleId") String articleId) {
        try {
            logger.info("获取文章:[{}]评论列表", articleId);
            List<ArticleCommentDto> commentDtos = articleCommentService.commentList(articleId);
            Success ok = new Success(200, commentDtos, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

}
