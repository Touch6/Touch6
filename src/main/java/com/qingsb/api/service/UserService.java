package com.qingsb.api.service;

import com.qingsb.core.exception.CoreException;
import com.qingsb.params.LoginParam;
import com.qingsb.params.PerfectInfoParam;
import com.qingsb.params.RegisterParam;

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

    /** 用户登录，登录成功返回uid
     * @param loginParam
     * @return
     * @throws CoreException
     */
    String login(LoginParam loginParam) throws CoreException;

    /** 完善用户信息，单项更新
     * @param perfectInfoParam
     * @throws Exception
     */
    void perfectUserInfo(PerfectInfoParam perfectInfoParam) throws CoreException;
}
