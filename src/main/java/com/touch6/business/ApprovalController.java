package com.touch6.business;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.ArticleService;
import com.touch6.business.controller.ArticleController;
import com.touch6.business.dto.article.ArticleDto;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by PAX on 2017/4/14.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/api/v1/article")
public class ApprovalController {
    private static final Logger logger = LoggerFactory.getLogger(ApprovalController.class);

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity write(@RequestBody ArticleDto articleDto) {
        try {
            logger.info("接收到要保存的文章信息:[{}]", JSONObject.toJSONString(articleDto));
            ArticleDto article = articleService.writeArticle(articleDto);
            Success ok = new Success(200, article, "保存成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
