package com.touch6.business.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.system.SystemService;
import com.touch6.business.entity.system.Auth;
import com.touch6.business.entity.system.Menu;
import com.touch6.business.entity.system.Module;
import com.touch6.business.entity.system.Role;
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

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/system")
public class SystemController {
    private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addAuth(@RequestBody Auth auth) {
        try {
            logger.info("接收到权限:[{}]", JSONObject.toJSONString(auth));
            systemService.addAuth(auth);
            Success ok = new Success(200, "添加成功", "添加权限成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/{authId}/role", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addRole(@PathVariable("authId") Long authId,
                                  @RequestBody Role role) {
        try {
            logger.info("接收到角色:[{}]", JSONObject.toJSONString(role));
            systemService.addRole(authId, role);
            Success ok = new Success(200, "添加成功", "添加角色成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/module", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addModule(@RequestBody Module module) {
        try {
            logger.info("接收到模块:[{}]", JSONObject.toJSONString(module));
            systemService.addModule(module);
            Success ok = new Success(200, "添加成功", "添加模块成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/module/{moduleId}/menu", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addMenu(@PathVariable("moduleId") Long moduleId,
                                  @RequestBody Menu menu) {
        try {
            logger.info("接收到菜单:[{}]", JSONObject.toJSONString(menu));
            systemService.addMenu(moduleId,menu);
            Success ok = new Success(200, "添加成功", "添加菜单成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

}
