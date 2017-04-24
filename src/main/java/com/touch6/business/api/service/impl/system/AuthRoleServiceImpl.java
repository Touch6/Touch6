package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.AuthRoleService;
import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.User;
import com.touch6.business.entity.system.*;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.business.mybatis.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;
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
public class AuthRoleServiceImpl implements AuthRoleService {
    private static final Logger logger = LoggerFactory.getLogger(AuthRoleServiceImpl.class);
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
    private RouteMybatisDao routeMybatisDao;
    @Autowired
    private Validator validator;

    @Override
    @Transactional
    public AuthRole assignAuthRole(Long authId, Long roleId) {
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
        return authRole;
    }


    @Override
    @Transactional
    public AuthRole updateAuthRole(Long authId, Long roleId, Long newAuthId) {
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
        return authRole;
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
    public PageObject<AuthRole> findAuthRoles(int page, int pageSize) {
        logger.info("获取所有权限角色page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<AuthRole> authRoles = authRoleMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<AuthRole> pageInfo = new PageInfo<AuthRole>(authRoles);

        PageObject<AuthRole> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(authRoles);
        return pageObject;
    }
}
