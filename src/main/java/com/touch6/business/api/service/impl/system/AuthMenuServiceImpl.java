package com.touch6.business.api.service.impl.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public void assignAuthMenu(JSONObject authmenu) {
        Long menuId = authmenu.getLong("menuId");
        List<Long> authIds = Lists.newArrayList();
        JSONArray authList = authmenu.getJSONArray("authList");
        if (authList.size() > 0) {
            for (int i = 0; i < authList.size(); i++) {
                JSONObject obj = authList.getJSONObject(i);
                boolean checked = obj.getBoolean("checked");
                Long authId = obj.getLong("authId");
                if (checked) {
                    authIds.add(authId);
                }
            }
        }
        //先删除原来的配置
        int deleted = authMenuMybatisDao.deleteAuthMenuByMenuId(menuId);
        logger.info("删除原配置:[{}]",deleted);
        //然后插入新的配置
        Map params = new HashMap();
        params.put("menuId", menuId);
        params.put("authIds", authIds);
        int inserted = authMenuMybatisDao.insertAuthMenuInBatch(params);
        logger.info("新插入配置:[{}]",inserted);
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
    public JSONObject findAllAuthmenuByMenuId(Long menuId) {
        Menu menu = menuMybatisDao.findByMenuId(menuId);
        List<AuthMenu> authMenuList = authMenuMybatisDao.findAllAuthmenuByMenuId(menuId);
        JSONObject out = new JSONObject();
        out.put("menuId", menuId);
        out.put("menuName", menu.getName());
        List<JSONObject> list = Lists.newArrayList();
        if (authMenuList.size() > 0) {
            for (AuthMenu am : authMenuList) {
                JSONObject obj = new JSONObject();
                obj.put("authId", am.getAuthId());
                obj.put("authName", am.getAuthName());
                if (am.getMenuId() == null) {
                    obj.put("checked", false);
                } else {
                    obj.put("checked", true);
                }
                list.add(obj);
            }
        }
        out.put("authList", list);
        return out;
    }
}
