package com.touch6.business.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.system.SystemService;
import com.touch6.business.entity.system.*;
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

    @RequestMapping(value = "/role", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addRole(@RequestBody Role role) {
        try {
            logger.info("接收到角色:[{}]", JSONObject.toJSONString(role));
            systemService.addRole(role);
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
            systemService.addMenu(moduleId, menu);
            Success ok = new Success(200, "添加成功", "添加菜单成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/user/role", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity assignUserRole(@RequestBody UserRole userRole) {
        try {
            logger.info("接收到用户角色配置:[{}]", JSONObject.toJSONString(userRole));
            systemService.assignUserRole(userRole.getUserId(), userRole.getRoleId());
            Success ok = new Success(200, "添加成功", "配置用户角色成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/role/auth", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity assignRoleAuth(@RequestBody AuthRole authRole) {
        try {
            logger.info("接收到角色权限配置:[{}]", JSONObject.toJSONString(authRole));
            systemService.assignAuthRole(authRole.getAuthId(), authRole.getRoleId());
            Success ok = new Success(200, "添加成功", "配置角色权限成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/menu/auth", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity assignMenuAuth(@RequestBody AuthMenu authMenu) {
        try {
            logger.info("接收到菜单权限配置:[{}]", JSONObject.toJSONString(authMenu));
            systemService.assignAuthMenu(authMenu.getAuthId(), authMenu.getMenuId());
            Success ok = new Success(200, "添加成功", "配置菜单权限成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/common/modules", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findCommonModules(@RequestParam("roleId") long roleId) {
        try {
            logger.info("获取公共的模块roleId:[{}]", roleId);
            List<Module> moduleList = systemService.findCommonModules(roleId);
            Success ok = new Success(200, moduleList, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/loginuser/modules", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findModulesByLoginUser(@RequestParam("token") String token) {
        try {
            logger.info("获取登录用户拥有的模块token:[{}]", token);
            List<Module> moduleList = systemService.findModulesByLoginUser(token);
            Success ok = new Success(200, moduleList, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/role", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateRole(@RequestBody Role role) {
        try {
            logger.info("接收到角色修改:[{}]", JSONObject.toJSONString(role));
            systemService.updateRole(role);
            Success ok = new Success(200, "修改成功", "修改角色信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateAuth(@RequestBody Auth auth) {
        try {
            logger.info("接收到权限修改:[{}]", JSONObject.toJSONString(auth));
            systemService.updateAuth(auth);
            Success ok = new Success(200, "修改成功", "修改权限信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/module", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateModule(@RequestBody Module module) {
        try {
            logger.info("接收到模块修改:[{}]", JSONObject.toJSONString(module));
            systemService.updateModule(module);
            Success ok = new Success(200, "修改成功", "修改模块信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/menu", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateMenu(@RequestBody Menu menu) {
        try {
            logger.info("接收到菜单修改:[{}]", JSONObject.toJSONString(menu));
            systemService.updateMenu(menu);
            Success ok = new Success(200, "修改成功", "修改菜单信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/role", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateUserRole(@RequestBody UserRole userRole) {
        try {
            logger.info("接收到用户角色修改:[{}]", JSONObject.toJSONString(userRole));
            systemService.updateUserRole(userRole.getUserId(),userRole.getRoleId(),userRole.getNewRoleId());
            Success ok = new Success(200, "修改成功", "修改用户角色信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/role", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateAuthRole(@RequestBody AuthRole authRole) {
        try {
            logger.info("接收到角色权限修改:[{}]", JSONObject.toJSONString(authRole));
            systemService.updateAuthRole(authRole.getAuthId(),authRole.getRoleId(),authRole.getNewAuthId());
            Success ok = new Success(200, "修改成功", "修改角色权限信息成功");
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
            systemService.updateAuthMenu(authMenu.getAuthId(),authMenu.getMenuId(),authMenu.getNewAuthId());
            Success ok = new Success(200, "修改成功", "修改菜单权限信息成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity roleList() {
        try {
            logger.info("获取角色列表");
            List<Role> roles=systemService.roleList();
            Success ok = new Success(200, roles, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auths", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity authList() {
        try {
            logger.info("获取权限列表");
            List<Auth> auths=systemService.authList();
            Success ok = new Success(200, auths, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/modules", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity moduleList() {
        try {
            logger.info("获取模块列表");
            List<Module> modules=systemService.moduleList();
            Success ok = new Success(200, modules, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/menus", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity menuList() {
        try {
            logger.info("获取菜单列表");
            List<Menu> menus=systemService.menuList();
            Success ok = new Success(200, menus, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findRoleById(@PathVariable("roleId")Long roleId) {
        try {
            logger.info("获取角色:[{}]列表",roleId);
            Role role=systemService.findByRoleId(roleId);
            Success ok = new Success(200, role, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/{authId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findAuthById(@PathVariable("authId")Long authId) {
        try {
            logger.info("获取权限:[{}]信息",authId);
            Auth auth=systemService.findByAuthId(authId);
            Success ok = new Success(200, auth, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/module/{moduleId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findModuleById(@PathVariable("moduleId")Long moduleId) {
        try {
            logger.info("获取模块:[{}]信息",moduleId);
            Module module=systemService.findByModuleId(moduleId);
            Success ok = new Success(200, module, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/menu/{menuId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findMenuById(@PathVariable("menuId")Long menuId) {
        try {
            logger.info("获取菜单:[{}]信息",menuId);
            Menu menu=systemService.findByMenuId(menuId);
            Success ok = new Success(200, menu, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/module/{moduleId}/menus", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity findMenusById(@PathVariable("moduleId")Long moduleId) {
        try {
            logger.info("获取模块:[{}]菜单列表信息",moduleId);
            List<Menu> menus=systemService.findMenusByModuleId(moduleId);
            Success ok = new Success(200, menus, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
