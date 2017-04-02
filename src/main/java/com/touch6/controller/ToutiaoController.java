package com.touch6.controller;

import com.touch6.api.service.ToutiaoService;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
import com.touch6.dto.entity.news.ToutiaoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/api/v1/toutiao")
public class ToutiaoController {
    private static final Logger logger = LoggerFactory.getLogger(ToutiaoController.class);

    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(value = "overview", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity pull(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                               @RequestParam(value = "phone", defaultValue = "30") int pageSize) {
        try {
            List<ToutiaoDto> toutiaoDtos = toutiaoService.listToutiao(pageNo, pageSize);
            Success ok = new Success(200, toutiaoDtos, "更新成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
