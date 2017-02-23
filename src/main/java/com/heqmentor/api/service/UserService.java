package com.heqmentor.api.service;

import com.heqmentor.dto.entity.RegisterDto;
import com.heqmentor.dto.entity.UserDto;

/**
 * Created by zhuxl on 2015/5/20.
 */
public interface UserService {

    /**
     * 用户注册，最少包含手机号，登录账号，登录密码
     *
     * @param registerDto
     * @throws Exception
     */
    void register(RegisterDto registerDto) throws Exception;

    /**
     * 添加用户
     *
     * @param userDto
     */
    void addUser(UserDto userDto) throws Exception;
}
