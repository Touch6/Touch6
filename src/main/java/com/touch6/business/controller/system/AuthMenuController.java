package com.touch6.business.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.system.AuthMenuService;
import com.touch6.business.entity.system.AuthMenu;
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
@RequestMapping(value = "/system/authmenu")
public class AuthMenuController {
    private static final Logger logger = LoggerFactory.getLogger(AuthMenuController.class);

    @Autowired
    private AuthMenuService authMenuService;

    @RequestMapping(value = "/menu/auth", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity assignMenuAuth(@RequestBody AuthMenu authMenu) {
        try {
            logger.info("接收到菜单权限配置:[{}]", JSONObject.toJSONString(authMenu));
            AuthMenu am = authMenuService.assignAuthMenu(authMenu.getAuthId(), authMenu.getMenuId());
            Success ok = new Success(200, am, "配置菜单权限成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/menu", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateAuthMenu(@RequestBody AuthMenu authMenu) {
        try {
            logger.info("接收到菜单权限修改:[{}]", JSONObject.toJSONString(authMenu));
            AuthMenu am = authMenuService.updateAuthMenu(authMenu.getAuthId(), authMenu.getMenuId(), authMenu.getNewAuthId());
            Success ok = new Success(200, am, "修改菜单权限信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/auth/menu", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteAuthMenu(@RequestBody AuthMenu authMenu) {
        try {
            logger.info("删除菜单权限:[{}]", JSONObject.toJSONString(authMenu));
            authMenuService.deleteAuthMenu(authMenu);
            Success ok = new Success(200, "删除成功", "删除成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "page/authmenus", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity pageAuthMenus(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            PageObject<AuthMenu> pageObject = authMenuService.findAuthMenus(page, pageSize);
            Success ok = new Success(200, pageObject, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }


}
