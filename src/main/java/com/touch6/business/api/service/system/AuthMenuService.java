package com.touch6.business.api.service.system;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface AuthMenuService {
    void assignAuthMenu(JSONObject authmenu);

    JSONObject findAllAuthmenuByMenuId(Long menuId);
}
