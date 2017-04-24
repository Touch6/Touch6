package com.touch6.business.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.system.AuthService;
import com.touch6.business.entity.system.Auth;
import com.touch6.commons.PageObject;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/system/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;
    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addAuth(@RequestBody Auth auth) {
        try {
            logger.info("接收到权限:[{}]", JSONObject.toJSONString(auth));
            Auth a = authService.addAuth(auth);
            Success ok = new Success(200, a, "添加权限成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateAuth(@RequestBody Auth auth) {
        try {
            logger.info("接收到权限修改:[{}]", JSONObject.toJSONString(auth));
            Auth a = authService.updateAuth(auth);
            Success ok = new Success(200, a, "修改权限信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity authList() {
        try {
            logger.info("获取权限列表");
            List<Auth> auths = authService.authList();
            Success ok = new Success(200, auths, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{authId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findAuthById(@PathVariable("authId") Long authId) {
        try {
            logger.info("获取权限:[{}]信息", authId);
            Auth auth = authService.findByAuthId(authId);
            Success ok = new Success(200, auth, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{authId}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteAuth(@PathVariable("authId") Long authId) {
        try {
            logger.info("删除权限:[{}]", authId);
            authService.deleteAuth(authId);
            Success ok = new Success(200, "删除成功", "删除成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "pageable", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity pageAuths(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        try {
            PageObject<Auth> authPageObject = authService.findAllAuths(page, pageSize);
            Success ok = new Success(200, authPageObject, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }


}
