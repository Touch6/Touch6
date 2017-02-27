package com.qingsb.api.service;

import com.qingsb.core.exception.CoreException;
import com.qingsb.dto.entity.RegisterDto;
import com.qingsb.dto.entity.UserDto;
import com.qingsb.enums.UserInfo;

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
    void register(RegisterDto registerDto) throws CoreException;

    /** 完善用户信息，单项更新
     * @param uid
     * @param info
     * @param type
     * @throws Exception
     */
    void perfectUserInfo(String uid,String info,UserInfo type) throws CoreException;
}
