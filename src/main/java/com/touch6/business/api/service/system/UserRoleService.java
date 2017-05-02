package com.touch6.business.api.service.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.entity.system.UserRole;
import com.touch6.commons.PageObject;

/**
 * Created by LONG on 2017/4/18.
 */
public interface UserRoleService {
    void assignUserRole(JSONObject userrole);

    JSONObject findAllUserroleByUserId(Long userId);

}
