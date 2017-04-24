package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.RouteService;
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
public class RouteServiceImpl implements RouteService {
    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);
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
    public Route addRoute(Route route) {
        String error = T6ValidatorUtil.validate(validator, route);
        if (error != null) {
            Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
            err.setDes(error);
            throw new CoreException(err);
        }
        Date time = new Date();
        if (route.getSuperId() == null) {
            route.setRank(1);
        } else {
            int superRank = routeMybatisDao.findSuperRankByRouteId(route.getSuperId());
            route.setRank(superRank + 1);
        }
        route.setCreateTime(time);
        route.setUpdateTime(time);
        int inserted = routeMybatisDao.insertRoute(route);
        if (inserted == 0) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
        return route;
    }

    @Override
    public Route findByRouteId(Long routeId) {
        return routeMybatisDao.findByRouteId(routeId);
    }

    @Override
    @Transactional
    public Route updateRoute(Route route) {
        String error = T6ValidatorUtil.validate(validator, route);
        if (error != null) {
            Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
            err.setDes(error);
            throw new CoreException(err);
        }
        Date time = new Date();
        if (route.getSuperId() == null) {
            route.setRank(1);
        } else {
            int superRank = routeMybatisDao.findSuperRankByRouteId(route.getSuperId());
            route.setRank(superRank + 1);
        }
        route.setUpdateTime(time);
        int updated = routeMybatisDao.updateRoute(route);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
        return route;
    }

    @Override
    public List<Route> findBySuperId(Long superId) {
        Map params = new HashMap();
        params.put("superId", superId);
        return routeMybatisDao.findBySuperId(params);
    }

    @Override
    @Transactional
    public void deleteRoute(Long routeId) {
        int deleted = routeMybatisDao.deleteRoute(routeId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
    }

    @Override
    public PageObject<Route> findAllRoutes(int page, int pageSize) {
        logger.info("获取所有路由page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<Route> routes = routeMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<Route> pageInfo = new PageInfo<Route>(routes);

        PageObject<Route> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(routes);
        return pageObject;
    }

}
