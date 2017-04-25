package com.touch6.business.api.service.system;

import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface RoleService {
    Role addRole(Role role);

    Role updateRole(Role role);

    List<Role> roleList();

    Role findByRoleId(Long roleId);

    void deleteRole(Long roleId);

    PageObject<Role> findAllRoles(int page, int pageSize);

    void lock(Long roleId);

    void unlock(Long roleId);
}
