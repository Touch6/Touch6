package com.touch6.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.ArticleCommentReplyService;
import com.touch6.business.api.service.ArticleCommentService;
import com.touch6.business.dto.article.ArticleCommentDto;
import com.touch6.business.dto.article.ArticleCommentReplyDto;
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
@RequestMapping(value = "/api/v1/article/comment/reply")
public class ArticleCommentReplyController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleCommentReplyController.class);

    @Autowired
    private ArticleCommentReplyService articleCommentReplyService;

    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addReply(@RequestBody ArticleCommentReplyDto replyDto) {
        try {
            logger.info("接收到文章评论:[{}]的回复:[{}]", replyDto.getCommentId(), JSONObject.toJSONString(replyDto));
            ArticleCommentReplyDto reply = articleCommentReplyService.addReply(replyDto);
            Success ok = new Success(200, reply, "回复成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity replytList(@RequestParam("commentId") String commentId) {
        try {
            logger.info("获取文章评论:[{}]回复列表", commentId);
            List<ArticleCommentReplyDto> replyDtos = articleCommentReplyService.replyList(commentId);
            Success ok = new Success(200, replyDtos, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

}
