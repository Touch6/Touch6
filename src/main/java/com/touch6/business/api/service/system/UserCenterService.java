package com.touch6.business.api.service.system;

import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.system.UserRole;
import com.touch6.commons.PageObject;

/**
 * Created by LONG on 2017/4/18.
 */
public interface UserCenterService {
    PageObject<UserDto> findAllUsers(int page,int pageSize);
}
