package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.UserRoleService;
import com.touch6.business.entity.User;
import com.touch6.business.entity.system.Role;
import com.touch6.business.entity.system.UserRole;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.business.mybatis.system.*;
import com.touch6.commons.PageObject;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.error.constant.CommonErrorConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.mapper.BeanMapper;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/4/18.
 */
@SuppressWarnings("ALL")
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
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
    public UserRole assignUserRole(Long userId, Long roleId) {
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
        return userRole;
    }

    @Override
    @Transactional
    public UserRole updateUserRole(Long userId, Long roleId, Long newRoleId) {
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
        return userRole;
    }

    @Override
    @Transactional
    public void deleteUserRole(UserRole userRole) {
        int deleted = userRoleMybatisDao.deleteUserRole(userRole);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    public PageObject<UserRole> findUserRoles(int page, int pageSize) {
        logger.info("获取所有用户角色page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<UserRole> userRoles = userRoleMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<UserRole> pageInfo = new PageInfo<UserRole>(userRoles);

        PageObject<UserRole> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(userRoles);
        return pageObject;
    }
}
