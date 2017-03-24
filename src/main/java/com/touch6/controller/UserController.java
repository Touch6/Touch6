package com.touch6.controller;

import com.touch6.api.service.UserService;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
import com.touch6.dto.entity.UserDto;
import com.touch6.params.LoginParam;
import com.touch6.params.PerfectInfoParam;
import com.touch6.params.RegisterParam;
import com.touch6.params.UniqueParam;
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
            UserDto userDto = userService.login(loginParam);
            Success ok = new Success(200, userDto, "恭喜你!登录成功");
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

    @RequestMapping(value = "info", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity userInfo(@RequestBody UniqueParam uniqueParam) {
        try {
            UserDto userDto=userService.getUserInfo(uniqueParam.getUid());
            return new ResponseEntity(userDto, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
