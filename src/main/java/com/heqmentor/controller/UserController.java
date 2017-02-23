package com.heqmentor.controller;

import com.alibaba.fastjson.JSONObject;
import com.heqmentor.api.service.UserService;
import com.heqmentor.core.exception.Error;
import com.heqmentor.core.info.Success;
import com.heqmentor.dto.entity.RegisterDto;
import com.heqmentor.dto.entity.UserDto;
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
    public ResponseEntity register(@RequestBody RegisterDto registerDto) {
        try {
            userService.register(registerDto);
            Success ok = new Success(200, null, "注册成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (Exception e) {
            Error error = new Error(500, "系统异常", e.getMessage());
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
    }
}
