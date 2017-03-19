package com.knowincloud.controller;

import com.knowincloud.api.service.MobileService;
import com.knowincloud.core.exception.CoreException;
import com.knowincloud.core.exception.Error;
import com.knowincloud.core.info.Success;
import com.knowincloud.dto.entity.MobileCodeDto;
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
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.info("系统异常:",e);
            Error error=new Error(500,"系统异常",e.getMessage());
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "code", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity generateCode(@RequestParam("mobile") String mobile) {
        try {
            mobileService.generateMobileCode(mobile);
            Success ok=new Success(200,"操作成功","生成验证码成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            logger.info("异常:", e);
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.info("系统异常:",e);
            Error error=new Error(500,"系统异常",e.getMessage());
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
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.info("系统异常:",e);
            Error error=new Error(500,"系统异常",e.getMessage());
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
