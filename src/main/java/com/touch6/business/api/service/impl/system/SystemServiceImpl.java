package com.touch6.business.api.service.impl.system;

import com.touch6.business.api.service.system.SystemService;
import com.touch6.business.entity.system.*;
import com.touch6.business.mybatis.system.*;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.error.constant.CommonErrorConstant;
import com.touch6.core.exception.error.constant.SystemErrorConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    private RoleAuthMybatisDao roleAuthMybatisDao;
    @Autowired
    private AuthMenuMybatisDao authMenuMybatisDao;


    @Override
    @Transactional
    public void addRole(Long authId, Role role) {
        Auth auth = authMybatisDao.findByAuthId(authId);
        if (auth == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        Date time = new Date();
        role.setCreateTime(time);
        role.setUpdateTime(time);
        int insertedRole = roleMybatisDao.insertRole(role);
        if (insertedRole == 1) {
//            RoleAuth roleAuth = new RoleAuth();
//            roleAuth.setAuthId(authId);
//            roleAuth.setRoleId(role.getRoleId());
//            int insertedMap = roleAuthMybatisDao.insertRoleAuth(roleAuth);
//            if (insertedMap == 0) {
//                throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
//            }
        } else {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }

    @Override
    @Transactional
    public void addAuth(Auth auth) {
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
        Module module = moduleMybatisDao.findByModuleId(moduleId);
        if (module == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        menu.setModuleId(moduleId);
        Date time = new Date();
        menu.setCreateTime(time);
        menu.setUpdateTime(time);
        int insertedMenu = menuMybatisDao.insertMenu(menu);
        if (insertedMenu == 0) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }
}
