package com.heqmentor.controller;

import com.alibaba.fastjson.JSONObject;
import com.heqmentor.api.service.MobileService;
import com.heqmentor.api.service.UserService;
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
            JSONObject ok = new JSONObject();
            ok.put("success", true);
            ok.put("msg", "恭喜你，该号码可用");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("msg", e.getMessage());
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "code", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity generateCode(@RequestParam("mobile") String mobile) {
        try {
            String code = mobileService.generateMobileCode(mobile);
            JSONObject ok = new JSONObject();
            ok.put("success", true);
            ok.put("msg", "生成验证码成功");
            ok.put("code", code);
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("msg", e.getMessage());
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity verify(@RequestBody MobileCodeDto mobileCode) {
        try {
            mobileService.verifyMobileCode(mobileCode.getMobile(), mobileCode.getPresCode());
            JSONObject ok = new JSONObject();
            ok.put("success", true);
            ok.put("msg", "恭喜你，验证成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("msg", e.getMessage());
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
    }
}
