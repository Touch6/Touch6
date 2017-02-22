package com.heqmentor.api.service;

import com.heqmentor.dto.entity.UserDto;

/**
* Created by zhuxl on 2015/5/20.
*/
public interface UserService {

    /**添加用户
     * @param userDto
     */
    void addUser(UserDto userDto) throws Exception;
}
