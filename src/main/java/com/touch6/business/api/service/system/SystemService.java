package com.touch6.business.api.service.system;

import com.touch6.business.entity.system.*;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface SystemService {
    void addRole(Role role);

    void addAuth(Auth auth);

    void addModule(Module module);

    void addMenu(Long moduleId, Menu menu);

    void assignUserRole(Long userId, Long roleId);

    void assignAuthRole(Long authId, Long roleId);

    void assignAuthMenu(Long authId, Long menuId);

    Role updateRole(Role role);

    Auth updateAuth(Auth auth);

    Module updateModule(Module module);

    Menu updateMenu(Menu menu);

    UserRole updateUserRole(Long userId, Long roleId);

    AuthRole updateAuthRole(Long authId, Long roleId);

    AuthMenu updateAuthMenu(Long authId, Long menuId);

    List<Module> findCommonModules(Long roleId);

    List<Module> findModulesByLoginUser(String token);

}
