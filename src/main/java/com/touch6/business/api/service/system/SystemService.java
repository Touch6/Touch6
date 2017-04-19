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

    void updateUserRole(Long userId, Long roleId, Long newRoleId);

    void updateAuthRole(Long authId, Long roleId, Long newAuthId);

    void updateAuthMenu(Long authId, Long menuId, Long newAuthId);

    List<Module> findCommonModules(Long roleId);

    List<Module> findModulesByLoginUser(String token);

    List<Role> roleList();

    List<Auth> authList();

    List<Menu> menuList();

    List<Module> moduleList();

    Role findByRoleId(Long roleId);

    Auth findByAuthId(Long authId);

    Module findByModuleId(Long moduleId);

    Menu findByMenuId(Long menuId);

    List<Menu> findMenusByModuleId(Long moduleId);

    void deleteRole(Long roleId);

    void deleteAuth(Long authId);

    void deleteModule(Long moduleId);

    void deleteMenu(Long menuId);

    void deleteAuthMenu(AuthMenu authMenu);

    void deleteAuthRole(AuthRole authRole);

    void deleteUserRole(UserRole userRole);

}
