package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.touch6.business.api.service.system.AuthMenuService;
import com.touch6.business.entity.system.Auth;
import com.touch6.business.entity.system.AuthMenu;
import com.touch6.business.entity.system.Menu;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/4/18.
 */
@SuppressWarnings("ALL")
@Service
public class AuthMenuServiceImpl implements AuthMenuService {
    private static final Logger logger = LoggerFactory.getLogger(AuthMenuServiceImpl.class);
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
    public void assignAuthMenu(Long[] authIdArray, Long[] menuIdArray) {
        List authIds= Arrays.asList(authIdArray);
        List menuIds= Arrays.asList(menuIdArray);
        int authCount = authMybatisDao.findCountByAuthIds(authIds);
        if (authCount != authIds.size()) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        int menuCount = menuMybatisDao.findCountByMenuIds(menuIds);
        if (menuCount != menuIds.size()) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        for (int i = 0; i < authIds.size(); i++) {
            Map params = new HashMap();
            params.put("authId", authIds.get(i));
            params.put("menuIds",menuIds);
            int inserted = authMenuMybatisDao.insertAuthMenuInBatch(params);
            if (inserted == 0) {
                throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
            }
            logger.info("添加authId:[{}]配置:[{}]个",authIds.get(i),menuIds.size());
        }
    }


    @Override
    @Transactional
    public AuthMenu updateAuthMenu(Long authId, Long menuId, Long newAuthId) {
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
        return authMenu;
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
    public PageObject<AuthMenu> findAuthMenus(int page, int pageSize) {
        logger.info("获取所有权限菜单page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<AuthMenu> authMenus = authMenuMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<AuthMenu> pageInfo = new PageInfo<AuthMenu>(authMenus);

        PageObject<AuthMenu> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(authMenus);
        return pageObject;
    }

    @Override
    public List<AuthMenu> findAllAuthmenuByMenuId(Long menuId) {
        return authMenuMybatisDao.findAllAuthmenuByMenuId(menuId);
    }
}
