package com.touch6.business.api.service;

import com.touch6.core.exception.CoreException;
import com.touch6.business.entity.UserDto;
import com.touch6.business.params.LoginParam;
import com.touch6.business.params.PerfectInfoParam;
import com.touch6.business.params.RegisterParam;

/**
 * Created by zhuxl on 2015/5/20.
 */
public interface UserService {

    /**
     * 用户注册，最少包含手机号，登录账号，登录密码
     *
     * @param registerParam
     * @throws Exception
     */
    void register(RegisterParam registerParam) throws CoreException;

    /**
     * 用户登录，登录成功返回uid
     *
     * @param loginParam
     * @return
     * @throws CoreException
     */
    UserDto login(LoginParam loginParam) throws CoreException;

    /**
     * 完善用户信息，单项更新
     *
     * @param perfectInfoParam
     * @throws Exception
     */
    void perfectUserInfo(PerfectInfoParam perfectInfoParam) throws CoreException;

    /**获取用户详情
     * @param uid
     * @return
     * @throws CoreException
     */
    UserDto getUserInfo(String uid) throws CoreException;
}
