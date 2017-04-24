package com.touch6.business.api.service.system;

import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface UserRoleService {
    UserRole assignUserRole(Long userId, Long roleId);

    UserRole updateUserRole(Long userId, Long roleId, Long newRoleId);

    void deleteUserRole(UserRole userRole);

    PageObject<UserRole> findUserRoles(int page, int pageSize);

}
