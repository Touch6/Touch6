package com.touch6.business.controller;

import com.touch6.business.api.service.ToolsService;
import com.touch6.business.output.DencryptOutput;
import com.touch6.business.params.DencryptParam;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.Error;
import com.touch6.core.info.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            String result = toolsService.dateFormat(src, format, type);
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
            String result = toolsService.codec(src, type);
            Success ok = new Success(200, result, "编解码成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "dencrypt", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity dencrypt(@RequestBody DencryptParam dencryptParam) {
        try {
            DencryptOutput output = toolsService.dencrypt(dencryptParam);
            Success ok = new Success(200, output, "加解密成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
