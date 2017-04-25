package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.MenuService;
import com.touch6.business.entity.system.Menu;
import com.touch6.business.entity.system.Module;
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
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
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
    public Menu addMenu(Long moduleId, Menu menu) {
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
        return menu;
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
    public List<Menu> menuList() {
        return menuMybatisDao.findAll();
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
    public void deleteMenu(Long menuId) {
        Menu menu=menuMybatisDao.findByMenuId(menuId);
        if(menu==null){
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_NOT_EXISTED));
        }
        if(menu.getLocked()==1){
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
        int deleted = menuMybatisDao.deleteMenu(menuId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }

    @Override
    public PageObject<Menu> findAllMenus(int page, int pageSize) {
        logger.info("获取所有菜单page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<Menu> menus = menuMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<Menu> pageInfo = new PageInfo<Menu>(menus);

        PageObject<Menu> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(menus);
        return pageObject;
    }

    @Override
    @Transactional
    public void lock(Long menuId) {
        int locked = menuMybatisDao.lock(menuId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
    }

    @Override
    @Transactional
    public void unlock(Long menuId) {
        int locked = menuMybatisDao.unlock(menuId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_UNLOCKED));
        }
    }
}
