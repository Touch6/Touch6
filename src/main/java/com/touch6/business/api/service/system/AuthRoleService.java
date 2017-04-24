package com.touch6.business.api.service.system;

import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface AuthRoleService {
    AuthRole assignAuthRole(Long authId, Long roleId);

    AuthRole updateAuthRole(Long authId, Long roleId, Long newAuthId);

    void deleteAuthRole(AuthRole authRole);

    PageObject<AuthRole> findAuthRoles(int page, int pageSize);
}
