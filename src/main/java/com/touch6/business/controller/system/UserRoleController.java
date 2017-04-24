package com.touch6.business.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.system.UserRoleService;
import com.touch6.business.entity.system.UserRole;
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

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/system/userrole")
public class UserRoleController {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;
    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity assignUserRole(@RequestBody UserRole userRole) {
        try {
            logger.info("接收到用户角色配置:[{}]", JSONObject.toJSONString(userRole));
            UserRole ur = userRoleService.assignUserRole(userRole.getUserId(), userRole.getRoleId());
            Success ok = new Success(200, ur, "配置用户角色成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateUserRole(@RequestBody UserRole userRole) {
        try {
            logger.info("接收到用户角色修改:[{}]", JSONObject.toJSONString(userRole));
            UserRole ur = userRoleService.updateUserRole(userRole.getUserId(), userRole.getRoleId(), userRole.getNewRoleId());
            Success ok = new Success(200, ur, "修改用户角色信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "pageable", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity pageUserRoles(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            PageObject<UserRole> pageObject = userRoleService.findUserRoles(page, pageSize);
            Success ok = new Success(200, pageObject, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }



}
