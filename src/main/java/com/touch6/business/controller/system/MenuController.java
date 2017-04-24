package com.touch6.business.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.system.MenuService;
import com.touch6.business.entity.system.Menu;
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
@RequestMapping(value = "/system/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addMenu(@RequestBody Menu menu) {
        try {
            logger.info("接收到菜单:[{}]", JSONObject.toJSONString(menu));
            Menu m = menuService.addMenu(menu.getModuleId(), menu);
            Success ok = new Success(200, m, "添加菜单成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateMenu(@RequestBody Menu menu) {
        try {
            logger.info("接收到菜单修改:[{}]", JSONObject.toJSONString(menu));
            Menu m = menuService.updateMenu(menu);
            Success ok = new Success(200, m, "修改菜单信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findMenuById(@PathVariable("menuId") Long menuId) {
        try {
            logger.info("获取菜单:[{}]信息", menuId);
            Menu menu = menuService.findByMenuId(menuId);
            Success ok = new Success(200, menu, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findMenusById(@RequestParam("moduleId") Long moduleId) {
        try {
            logger.info("获取模块:[{}]菜单列表信息", moduleId);
            List<Menu> menus = menuService.findMenusByModuleId(moduleId);
            Success ok = new Success(200, menus, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "pageable", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity pageMenus(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        try {
            PageObject<Menu> menuPageObject = menuService.findAllMenus(page, pageSize);
            Success ok = new Success(200, menuPageObject, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
