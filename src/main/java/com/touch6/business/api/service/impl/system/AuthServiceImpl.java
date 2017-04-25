package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.AuthService;
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
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
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
    public Auth addAuth(Auth auth) {
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
        return auth;
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
    public List<Auth> authList() {
        return authMybatisDao.findAll();
    }

    @Override
    public Auth findByAuthId(Long authId) {
        return authMybatisDao.findByAuthId(authId);
    }


    @Override
    @Transactional
    public void deleteAuth(Long authId) {
        Auth auth=authMybatisDao.findByAuthId(authId);
        if(auth==null){
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_NOT_EXISTED));
        }
        if (auth.getLocked() == 1) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
        int deleted = authMybatisDao.deleteAuth(authId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    public PageObject<Auth> findAllAuths(int page, int pageSize) {
        logger.info("获取所有权限page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<Auth> auths = authMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<Auth> pageInfo = new PageInfo<Auth>(auths);

        PageObject<Auth> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(auths);
        return pageObject;
    }

    @Override
    @Transactional
    public void lock(Long authId) {
        int locked = authMybatisDao.lock(authId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
    }

    @Override
    @Transactional
    public void unlock(Long authId) {
        int locked = authMybatisDao.unlock(authId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_UNLOCKED));
        }
    }
}
