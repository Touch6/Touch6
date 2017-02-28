package com.qingsb.controller;

import com.qingsb.api.service.UserService;
import com.qingsb.core.exception.CoreException;
import com.qingsb.core.info.Success;
import com.qingsb.params.LoginParam;
import com.qingsb.params.PerfectInfoParam;
import com.qingsb.params.RegisterParam;
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
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity register(@RequestBody RegisterParam registerParam) {
        try {
            userService.register(registerParam);
            Success ok = new Success(200, "注册成功", "恭喜你!注册成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity login(@RequestBody LoginParam loginParam) {
        try {
            String uid=userService.login(loginParam);
            Success ok = new Success(200, uid, "恭喜你!登录成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "perfect", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity perfectInfo(@RequestBody PerfectInfoParam infoParam) {
        try {
            userService.perfectUserInfo(infoParam);
            Success ok = new Success(200, "更新成功", "恭喜你!资料更新成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
