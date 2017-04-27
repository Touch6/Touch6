package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.RoleService;
import com.touch6.business.entity.system.Role;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.business.mybatis.system.*;
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
import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
@SuppressWarnings("ALL")
@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
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
    public Role addRole(Role role) {
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
            return role;
        } else {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
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
    public List<Role> roleList() {
        return roleMybatisDao.findAll();
    }

    @Override
    public Role findByRoleId(Long roleId) {
        return roleMybatisDao.findByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        Role role=roleMybatisDao.findByRoleId(roleId);
        if(role==null){
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_NOT_EXISTED));
        }
        if(role.getLocked()==1){
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
        int deleted = roleMybatisDao.deleteRole(roleId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }

    @Override
    public PageObject<Role> findAllRoles(int page, int pageSize) {
        logger.info("获取所有角色page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<Role> roles = roleMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);

        PageObject<Role> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(roles);
        return pageObject;
    }

    @Override
    @Transactional
    public void lock(Long roleId) {
        int locked = roleMybatisDao.lock(roleId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
    }

    @Override
    @Transactional
    public void unlock(Long roleId) {
        int locked = roleMybatisDao.unlock(roleId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_UNLOCKED));
        }
    }
}
