package com.touch6.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.ApprovalService;
import com.touch6.business.api.service.OpposeService;
import com.touch6.business.dto.common.ApprovalDto;
import com.touch6.business.dto.common.OpposeDto;
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
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/api/v1/oppose")
public class OpposeController {
    private static final Logger logger = LoggerFactory.getLogger(OpposeController.class);

    @Autowired
    private OpposeService opposeService;

    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity oppose(@RequestBody OpposeDto opposeDto) {
        try {
            logger.info("接收到反对:[{}]", JSONObject.toJSONString(opposeDto));
            Object obj = opposeService.makeOppose(opposeDto);
            Success ok = new Success(200, obj, "反对成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

}
