package com.touch6.business.api.service.system;

import com.touch6.business.entity.system.Auth;
import com.touch6.business.entity.system.Menu;
import com.touch6.business.entity.system.Module;
import com.touch6.business.entity.system.Role;

/**
 * Created by LONG on 2017/4/18.
 */
public interface SystemService {
    void addRole(Long authId, Role role);

    void addAuth(Auth auth);

    void addModule(Module module);

    void addMenu(Long moduleId,Menu menu);
}
