package com.touch6.business.api.service.impl.system;

import com.touch6.business.api.service.system.SystemService;
import com.touch6.business.entity.User;
import com.touch6.business.entity.system.*;
import com.touch6.business.enums.MenuStatus;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.business.mybatis.system.*;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.Error;
import com.touch6.core.exception.error.constant.CommonErrorConstant;
import com.touch6.core.exception.error.constant.SystemErrorConstant;
import com.touch6.utils.T6ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.mapper.BeanMapper;

import javax.validation.Validator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/4/18.
 */
@SuppressWarnings("ALL")
@Service
public class SystemServiceImpl implements SystemService {
    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);
    @Autowired
    private RoleMybatisDao roleMybatisDao;
    @Autowired
    private AuthMybatisDao authMybatisDao;
    @Autowired
    private ModuleMybatisDao moduleMybatisDao;
    @Autowired
    private MenuMybatisDao menuMybatisDao;
    @Autowired
    private UserRoleMybatisDao userRoleMybatisDao;
    @Autowired
    private AuthRoleMybatisDao authRoleMybatisDao;
    @Autowired
    private AuthMenuMybatisDao authMenuMybatisDao;
    @Autowired
    private UserMybatisDao userMybatisDao;
    @Autowired
    private Validator validator;

    @Override
    @Transactional
    public void addRole(Role role) {
        String error = T6ValidatorUtil.validate(validator, role);
        if (error != null) {
            Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
            err.setDes(error);
            throw new CoreException(err);
        }
        Date time = new Date();
        role.setCreateTime(time);
        role.setUpdateTime(time);
        int insertedRole = roleMybatisDao.insertRole(role);
        if (insertedRole == 1) {

        } else {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }

    @Override
    @Transactional
    public void addAuth(Auth auth) {
        String error = T6ValidatorUtil.validate(validator, auth);
        if (error != null) {
            Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
            err.setDes(error);
            throw new CoreException(err);
        }
        Date time = new Date();
        auth.setCreateTime(time);
        auth.setUpdateTime(time);
        int insert = authMybatisDao.insertAuth(auth);
        if (insert == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
    }

    @Override
    @Transactional
    public void addModule(Module module) {
        String error = T6ValidatorUtil.validate(validator, module);
        if (error != null) {
            Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
            err.setDes(error);
            throw new CoreException(err);
        }
        Date time = new Date();
        module.setCreateTime(time);
        module.setUpdateTime(time);
        int insert = moduleMybatisDao.insertModule(module);
        if (insert == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
    }

    @Override
    @Transactional
    public void addMenu(Long moduleId, Menu menu) {
        menu.setModuleId(moduleId);
        String error = T6ValidatorUtil.validate(validator, menu);
        if (error != null) {
            Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
            err.setDes(error);
            throw new CoreException(err);
        }

        Module module = moduleMybatisDao.findByModuleId(moduleId);
        if (module == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Date time = new Date();
        menu.setCreateTime(time);
        menu.setUpdateTime(time);
        int insertedMenu = menuMybatisDao.insertMenu(menu);
        if (insertedMenu == 0) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }

    @Override
    @Transactional
    public void assignUserRole(Long userId, Long roleId) {
        User user = userMybatisDao.findByUserId(userId);
        if (user == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Role role = roleMybatisDao.findByRoleId(roleId);
        if (role == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        UserRole userRole = userRoleMybatisDao.findByUserRole(new UserRole(userId, roleId));
        if (userRole != null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        int inserted = userRoleMybatisDao.insertUserRole(userRole);
        if (inserted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
    }

    @Override
    @Transactional
    public void assignAuthRole(Long authId, Long roleId) {
        Auth auth = authMybatisDao.findByAuthId(authId);
        if (auth == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Role role = roleMybatisDao.findByRoleId(roleId);
        if (role == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        AuthRole authRole = authRoleMybatisDao.findByAuthRole(new AuthRole(authId, roleId));
        if (authRole != null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        int inserted = authRoleMybatisDao.insertAuthRole(authRole);
        if (inserted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
    }

    @Override
    @Transactional
    public void assignAuthMenu(Long authId, Long menuId) {
        Auth auth = authMybatisDao.findByAuthId(authId);
        if (auth == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Menu menu = menuMybatisDao.findByMenuId(menuId);
        if (menu == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        AuthMenu authMenu = authMenuMybatisDao.findByAuthMenu(new AuthMenu(authId, menuId));
        if (authMenu != null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        int inserted = authMenuMybatisDao.insertAuthMenu(authMenu);
        if (inserted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        Date time = new Date();
        role.setUpdateTime(time);
        int updated = roleMybatisDao.updateRole(role);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        return role;
    }

    @Override
    @Transactional
    public Auth updateAuth(Auth auth) {
        Date time = new Date();
        auth.setUpdateTime(time);
        int updated = authMybatisDao.updateAuth(auth);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        return auth;
    }

    @Override
    @Transactional
    public Module updateModule(Module module) {
        Date time = new Date();
        module.setUpdateTime(time);
        int updated = moduleMybatisDao.updateModule(module);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        return module;
    }

    @Override
    @Transactional
    public Menu updateMenu(Menu menu) {
        Date time = new Date();
        menu.setUpdateTime(time);
        int updated = menuMybatisDao.updateMenu(menu);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        return menu;
    }

    @Override
    @Transactional
    public void updateUserRole(Long userId, Long roleId, Long newRoleId) {
        User user = userMybatisDao.findByUserId(userId);
        if (user == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Role role = roleMybatisDao.findByRoleId(roleId);
        if (role == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Role newRole = roleMybatisDao.findByRoleId(newRoleId);
        if (newRole == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        UserRole userRole = userRoleMybatisDao.findByUserRole(new UserRole(userId, roleId));
        if (userRole == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Map params = new HashMap();
        params.put("userId", userId);
        params.put("roleId", roleId);
        params.put("newRoleId", newRoleId);
        int updated = userRoleMybatisDao.updateUserRole(params);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public void updateAuthRole(Long authId, Long roleId, Long newAuthId) {
        Auth auth = authMybatisDao.findByAuthId(authId);
        if (auth == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Auth newAuth = authMybatisDao.findByAuthId(newAuthId);
        if (newAuth == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Role role = roleMybatisDao.findByRoleId(roleId);
        if (role == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        AuthRole authRole = authRoleMybatisDao.findByAuthRole(new AuthRole(authId, roleId));
        if (authRole == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Map params = new HashMap();
        params.put("authId", authId);
        params.put("roleId", roleId);
        params.put("newAuthId", newAuthId);
        int updated = authRoleMybatisDao.updateAuthRole(params);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }

    }

    @Override
    @Transactional
    public void updateAuthMenu(Long authId, Long menuId, Long newAuthId) {
        Auth auth = authMybatisDao.findByAuthId(authId);
        if (auth == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Auth newAuth = authMybatisDao.findByAuthId(newAuthId);
        if (newAuth == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Menu menu = menuMybatisDao.findByMenuId(menuId);
        if (menu == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        AuthMenu authMenu = authMenuMybatisDao.findByAuthMenu(new AuthMenu(authId, menuId));
        if (authMenu == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Map params = new HashMap();
        params.put("authId", authId);
        params.put("menuId", menuId);
        params.put("newAuthId", newAuthId);
        int updated = authMenuMybatisDao.updateAuthMenu(params);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public List<Module> findCommonModules(Long roleId) {
        List<Module> modules = moduleMybatisDao.findCommonModules(roleId);
        return modules;
    }


    @Override
    @Transactional
    public List<Module> findModulesByLoginUser(String token) {
        List<Module> modules = moduleMybatisDao.findModulesByLoginUserToken(token);
        return modules;
    }

    @Override
    public List<Role> roleList() {
        return roleMybatisDao.findAll();
    }

    @Override
    public List<Auth> authList() {
        return authMybatisDao.findAll();
    }

    @Override
    public List<Menu> menuList() {
        return menuMybatisDao.findAll();
    }

    @Override
    public List<Module> moduleList() {
        return moduleMybatisDao.findAll();
    }

    @Override
    public Role findByRoleId(Long roleId) {
        return roleMybatisDao.findByRoleId(roleId);
    }

    @Override
    public Auth findByAuthId(Long authId) {
        return authMybatisDao.findByAuthId(authId);
    }

    @Override
    public Module findByModuleId(Long moduleId) {
        return moduleMybatisDao.findByModuleId(moduleId);
    }

    @Override
    public Menu findByMenuId(Long menuId) {
        return menuMybatisDao.findByMenuId(menuId);
    }

    @Override
    public List<Menu> findMenusByModuleId(Long moduleId) {
        return menuMybatisDao.findByModuleId(moduleId);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        int deleted = roleMybatisDao.deleteRole(roleId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public void deleteAuth(Long authId) {
        int deleted = authMybatisDao.deleteAuth(authId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public void deleteModule(Long moduleId) {
        int deleted = moduleMybatisDao.deleteModule(moduleId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public void deleteMenu(Long menuId) {
        int deleted = menuMybatisDao.deleteMenu(menuId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public void deleteAuthMenu(AuthMenu authMenu) {
        int deleted = authMenuMybatisDao.deleteAuthMenu(authMenu);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public void deleteAuthRole(AuthRole authRole) {
        int deleted = authRoleMybatisDao.deleteAuthRole(authRole);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    @Transactional
    public void deleteUserRole(UserRole userRole) {
        int deleted = userRoleMybatisDao.deleteUserRole(userRole);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }
}
