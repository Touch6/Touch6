package com.touch6.business.controller;

import com.touch6.business.api.service.ToolsService;
import com.touch6.business.api.service.ToutiaoService;
import com.touch6.business.entity.news.ToutiaoDto;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
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
@RequestMapping(value = "/api/v1/tools")
public class ToolsController {
    private static final Logger logger = LoggerFactory.getLogger(ToolsController.class);

    @Autowired
    private ToolsService toolsService;

    @RequestMapping(value = "dateformat", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity dateFormat(@RequestParam(value = "src") String src,
                               @RequestParam(value = "format") String format,
                               @RequestParam(value = "type") String type) {
        try {
            String result = toolsService.dateFormat(src, format,type);
            Success ok = new Success(200, result, "格式化成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "codec", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity codec(@RequestParam(value = "src") String src,
                               @RequestParam(value = "type") String type) {
        try {
            String result = toolsService.codec(src,type);
            Success ok = new Success(200, result, "编解码成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
