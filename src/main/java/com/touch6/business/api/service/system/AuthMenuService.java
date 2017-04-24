package com.touch6.business.api.service.system;

import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface AuthMenuService {
    AuthMenu assignAuthMenu(Long authId, Long menuId);

    AuthMenu updateAuthMenu(Long authId, Long menuId, Long newAuthId);

    void deleteAuthMenu(AuthMenu authMenu);

    PageObject<AuthMenu> findAuthMenus(int page, int pageSize);
}
