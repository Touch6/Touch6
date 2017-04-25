package com.touch6.business.api.service.system;

import com.touch6.business.entity.system.Module;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface ModuleService {
    Module addModule(Module module);

    Module updateModule(Module module);

    List<Module> findCommonModules(Long roleId);

    List<Module> findModulesByLoginUser(String token);

    List<Module> moduleList();

    Module findByModuleId(Long moduleId);

    void deleteModule(Long moduleId);

    PageObject<Module> findAllModules(int page, int pageSize);

    List<ModuleSelectList> findList();

    PageObject<Module> findAllWithMenus(int page, int pageSize);

    void moveTop(Long moduleId);

    void moveUp(Long moduleId);

    void moveDown(Long moduleId);

    void lock(Long moduleId);

    void unlock(Long moduleId);
}
