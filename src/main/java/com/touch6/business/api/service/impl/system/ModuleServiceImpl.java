package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.ModuleService;
import com.touch6.business.entity.system.Menu;
import com.touch6.business.entity.system.Module;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.business.mybatis.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.Error;
import com.touch6.core.exception.error.constant.CommonErrorConstant;
import com.touch6.core.exception.error.constant.MenuErrorConstant;
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
public class ModuleServiceImpl implements ModuleService {
    private static final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);
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
    public Module addModule(Module module) {
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
        //设置其他module sort
        Map params = new HashMap();
        params.put("moduleId", module.getModuleId());
        params.put("sort", module.getSort());
        int updated = moduleMybatisDao.moveDownExceptThis(params);
        return module;
    }

    @Override
    @Transactional
    public Module updateModule(Module module) {
        Date time = new Date();
        module.setUpdateTime(time);
        int updated = moduleMybatisDao.updateModule(module);
        if (updated == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        return module;
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

    @Override
    public List<Module> moduleList() {
        return moduleMybatisDao.findAll();
    }

    @Override
    public Module findByModuleId(Long moduleId) {
        return moduleMybatisDao.findByModuleId(moduleId);
    }

    @Override
    @Transactional
    public void deleteModule(Long moduleId) {
        Module module = moduleMybatisDao.findByModuleId(moduleId);
        if (module == null) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_NOT_EXISTED));
        }
        if (module.getLocked() == 1) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
        //是否还有关联的menu
        int count = menuMybatisDao.findCountByModuleId(moduleId);
        if (count > 0) {
            throw new CoreException(ECodeUtil.getCommError(MenuErrorConstant.MENU_IS_NOT_DELETED));
        }
        int deleted = moduleMybatisDao.deleteModule(moduleId);
        if (deleted == 0) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
        Map params = new HashMap();
        params.put("moduleId", module.getModuleId());
        params.put("sort", module.getSort());
        int updated = moduleMybatisDao.moveUpExceptThis(params);
    }

    @Override
    public PageObject<Module> findAllModules(int page, int pageSize) {
        logger.info("获取所有模块page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<Module> modules = moduleMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<Module> pageInfo = new PageInfo<Module>(modules);

        PageObject<Module> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(modules);
        return pageObject;
    }

    @Override
    public List<ModuleSelectList> findList() {
        List<Module> modules = moduleMybatisDao.findModuleSelectList();
        return BeanMapper.mapList(modules, ModuleSelectList.class);
    }

    @Override
    public PageObject<Module> findAllWithMenus(int page, int pageSize) {
        logger.info("获取所有模块包括菜单page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<Module> modules = moduleMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<Module> pageInfo = new PageInfo<Module>(modules);

        PageObject<Module> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        if (modules.size() > 0) {
            for (int i = 0; i < modules.size(); i++) {
                Module module = modules.get(i);
                PageHelper.startPage(page, pageSize, true);
                List<Menu> menus = menuMybatisDao.findByModuleId(module.getModuleId());
                PageInfo<Menu> menuPageInfo = new PageInfo<Menu>(menus);

                PageObject<Menu> menuPageObject = BeanMapper.map(menuPageInfo, PageObject.class);
                menuPageObject.setList(menus);
                modules.get(i).setMenuPageObj(menuPageObject);
            }
        }
        pageObject.setList(modules);
        return pageObject;
    }

    @Override
    @Transactional
    public void moveTop(Long moduleId) {
        //判定当前模块是否为置顶，若是则取消操作
        Module module = moduleMybatisDao.findByModuleId(moduleId);
        if (module.getSort() == 1) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
        //当前模块sort设置为1,小于当前sort++
        int updated = moduleMybatisDao.moveTop(moduleId);
    }

    @Override
    @Transactional
    public void moveUp(Long moduleId) {
        //判定当前模块是否为置顶，若是则取消操作
        Module module = moduleMybatisDao.findByModuleId(moduleId);
        if (module.getSort() == 1) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
        int updated = moduleMybatisDao.moveUp(moduleId);
    }

    @Override
    @Transactional
    public void moveDown(Long moduleId) {
//判定当前模块是否为置顶，若是则取消操作
        Module module = moduleMybatisDao.findByModuleId(moduleId);
        int maxSort = moduleMybatisDao.findMaxSort();
        if (maxSort == module.getSort()) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_OPER_REPEAT));
        }
        int updated = moduleMybatisDao.moveDown(moduleId);
    }

    @Override
    @Transactional
    public void lock(Long moduleId) {
        int locked = moduleMybatisDao.lock(moduleId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_LOCKED));
        }
    }

    @Override
    @Transactional
    public void unlock(Long moduleId) {
        int locked = moduleMybatisDao.unlock(moduleId);
        if (locked == 0) {
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_RESOURCE_UNLOCKED));
        }
    }
}
