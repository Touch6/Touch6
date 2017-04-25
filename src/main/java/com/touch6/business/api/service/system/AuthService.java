package com.touch6.business.api.service.system;

import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.system.*;
import com.touch6.business.output.system.ModuleSelectList;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public interface AuthService {
    Auth addAuth(Auth auth);

    Auth updateAuth(Auth auth);

    List<Auth> authList();

    Auth findByAuthId(Long authId);

    void deleteAuth(Long authId);

    PageObject<Auth> findAllAuths(int page, int pageSize);

    void lock(Long authId);

    void unlock(Long authId);
}
