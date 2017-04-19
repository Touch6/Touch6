package com.touch6.business.api.service.impl.system;

import com.touch6.business.api.service.system.SystemService;
import com.touch6.business.entity.system.*;
import com.touch6.business.enums.MenuStatus;
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
import java.util.List;

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
        AuthRole authRole = new AuthRole(authId, roleId);
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
        AuthMenu authMenu = new AuthMenu(authId, menuId);
        int inserted = authMenuMybatisDao.insertAuthMenu(authMenu);
        if (inserted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    @Transactional
    public Auth updateAuth(Auth auth) {
        return null;
    }

    @Override
    @Transactional
    public Module updateModule(Module module) {
        return null;
    }

    @Override
    @Transactional
    public Menu updateMenu(Menu menu) {
        return null;
    }

    @Override
    @Transactional
    public UserRole updateUserRole(Long userId, Long roleId) {
        return null;
    }

    @Override
    @Transactional
    public AuthRole updateAuthRole(Long authId, Long roleId) {
        return null;
    }

    @Override
    @Transactional
    public AuthMenu updateAuthMenu(Long authId, Long menuId) {
        return null;
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
}
