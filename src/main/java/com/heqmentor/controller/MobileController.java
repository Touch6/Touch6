package com.heqmentor.controller;

import com.alibaba.fastjson.JSONObject;
import com.heqmentor.api.service.MobileService;
import com.heqmentor.api.service.UserService;
import com.heqmentor.core.exception.CoreException;
import com.heqmentor.core.exception.Error;
import com.heqmentor.core.info.Success;
import com.heqmentor.dto.entity.MobileCodeDto;
import com.heqmentor.dto.entity.RegisterDto;
import com.heqmentor.po.entity.MobileCode;
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
@RequestMapping(value = "/api/v1/mobile")
public class MobileController {
    private static final Logger logger = LoggerFactory.getLogger(MobileController.class);

    @Autowired
    private MobileService mobileService;

    @RequestMapping(value = "check", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity check(@RequestParam("mobile") String mobile) {
        try {
            mobileService.checkMobile(mobile);
            Success ok=new Success(200,null,"恭喜你，该号码可用");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            logger.info("异常:", e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Error error = new Error(500, "系统异常", e.getMessage());
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "code", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity generateCode(@RequestParam("mobile") String mobile) {
        try {
            String code = mobileService.generateMobileCode(mobile);
            Success ok=new Success(200,code,"生成验证码成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            logger.info("异常:", e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Error error = new Error(500, "系统异常", e.getMessage());
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity verify(@RequestBody MobileCodeDto mobileCode) {
        try {
            mobileService.verifyMobileCode(mobileCode.getMobile(), mobileCode.getPresCode());
            Success ok=new Success(200,null,"恭喜你，验证成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            logger.info("异常:", e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Error error = new Error(500, "系统异常", e.getMessage());
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
