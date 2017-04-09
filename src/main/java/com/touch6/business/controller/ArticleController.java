package com.touch6.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.ArticleService;
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
@RequestMapping(value = "/api/v1/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity write(@RequestParam("uid") String uid,
                                @RequestBody ArticleDto articleDto) {
        try {
            logger.info("接收到要保存的文章信息:[{}]", JSONObject.toJSONString(articleDto));
            ArticleDto article = articleService.writeArticle(uid, articleDto);
            Success ok = new Success(200, article, "保存成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity detail(@RequestParam("id") String id) {
        try {
            logger.info("查看文章:[{}]详细信息", id);
            ArticleDto article = articleService.articleDetail(id);
            Success ok = new Success(200, article, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity articles(@RequestParam("uid") String uid,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            logger.info("用户:[{}]查看文章列表page[{}],pageSize:[{}]", uid, page, pageSize);
            PageObject<ArticleDto> articles = articleService.articleList(uid, page, pageSize);
            Success ok = new Success(200, articles, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "types", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity types() {
        try {
            logger.info("查看文章类型列表");
            List<ArticleType> types = articleService.typeList();
            Success ok = new Success(200, types, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "categories", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity categories(@RequestParam(value = "parentCategory", defaultValue = "") String parentCategory) {
        try {
            logger.info("查看文章分类列表");
            List<ArticleCategory> categories = articleService.findCategoriesByParentCategory(parentCategory);
            Success ok = new Success(200, categories, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
