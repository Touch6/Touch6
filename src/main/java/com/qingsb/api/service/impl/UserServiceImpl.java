package com.qingsb.api.service.impl;


import com.qingsb.api.service.UserService;
import com.qingsb.core.exception.CoreException;
import com.qingsb.core.exception.ECodeUtil;
import com.qingsb.core.exception.error.constant.AuthErrorConstant;
import com.qingsb.core.exception.error.constant.CommonErrorConstant;
import com.qingsb.core.exception.error.constant.SystemErrorConstant;
import com.qingsb.core.exception.error.constant.UserInfoConstant;
import com.qingsb.dao.repository.mybatis.AuthMybatisDao;
import com.qingsb.dao.repository.mybatis.CertificateMybatisDao;
import com.qingsb.dao.repository.mybatis.ImageMybatisDao;
import com.qingsb.dao.repository.mybatis.UserMybatisDao;
import com.qingsb.enums.UserInfo;
import com.qingsb.params.LoginParam;
import com.qingsb.params.PerfectInfoParam;
import com.qingsb.params.RegisterParam;
import com.qingsb.po.entity.Auth;
import com.qingsb.po.entity.User;
import com.qingsb.util.PasswordEncryptionUtil;
import com.qingsb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.beanvalidator.BeanValidators;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhuxl@paxsz.com on 2016/7/25.
 */
@SuppressWarnings("ALL")
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMybatisDao userMybatisDao;
    @Autowired
    CertificateMybatisDao certificateMybatisDao;
    @Autowired
    ImageMybatisDao imageMybatisDao;
    @Autowired
    AuthMybatisDao authMybatisDao;
    @Autowired
    private Validator validator;

    @Override
    @Transactional
    public void register(RegisterParam registerParam) throws CoreException {
        BeanValidators.validateWithException(validator, registerParam);
        //判定密码和确认密码是否一样
        if (!registerParam.getPassword().equals(registerParam.getConfirmPassword())) {
            throw new CoreException(ECodeUtil.getCommError(UserInfoConstant.USER_INFO_PASSWORD_CONFIRM_ERROR));
        }

        User user = new User();
        String uid = StringUtil.generate32uuid();
        user.setUid(uid);
        user.setMobile(registerParam.getMobile());
        //insert user
        try {
            userMybatisDao.register(user);
        } catch (Exception e) {
            logger.info("插入用户信息异常，堆栈:", e);
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
        Auth auth = new Auth();
        String authId = StringUtil.generate32uuid();
        auth.setId(authId);
        auth.setUid(uid);
        auth.setLoginName(registerParam.getLoginName());
        String salt = StringUtil.generate32uuid();
        auth.setSalt(salt);
        auth.setPassword(PasswordEncryptionUtil.getEncryptedPassword(registerParam.getPassword(), salt));
        //insert auth
        try {
            authMybatisDao.insertAuth(auth);
        } catch (Exception e) {
            logger.info("插入登录信息异常，堆栈:", e);
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }

    @Override
    public String login(LoginParam loginParam) throws CoreException {
        //todo 加入登录日志
        String loginName = loginParam.getLoginName();
        String password = loginParam.getPassword();
        Auth auth = authMybatisDao.findAuthByLoginName(loginName);
        if (auth == null) {
            logger.info("通过登录名[{}]查询不到登录信息", loginName);
            throw new CoreException(ECodeUtil.getCommError(AuthErrorConstant.AUTH_NO_USER));
        }
        boolean success = PasswordEncryptionUtil.authenticate(password, auth.getPassword(), auth.getSalt());
        if (!success) {
            logger.info("登录账号[{}]密码[{}]错误", loginName, password);
            throw new CoreException(ECodeUtil.getCommError(AuthErrorConstant.AUTH_PASSWORD_ERROR));
        }
        return auth.getUid();
    }

    @Override
    public void perfectUserInfo(PerfectInfoParam perfectInfoParam) throws CoreException {
        try {
            UserInfo infoType = UserInfo.valueOf(perfectInfoParam.getType());
            String column = infoType.name().toLowerCase();
            Map params = new HashMap();
            params.put("uid", perfectInfoParam.getUid());
            params.put("column", column);
            params.put("value", perfectInfoParam.getInfo());
            logger.info("即将完善的信息参数:[{}]", params.toString());
            userMybatisDao.perfectUserInfo(params);
        } catch (Exception e) {
            logger.info("完善信息异常，堆栈:", e);
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

//    @Override
//    @Transactional
//    public void addUser(UserDto userDto) throws Exception {
//        User user = BeanMapper.map(userDto, User.class);
//        String uid = StringUtil.generate32uuid();
//        user.setUid(uid);
//        //加入用户信息
//        int res1 = userMybatisDao.updateUser(user);
//        if (res1 != 1) {
//            throw new Exception("添加用户失败");
//        }
//
//        if (user.getIdcard() != null) {
//            Certificate idcard = user.getIdcard();
//            String id = StringUtil.generate32uuid();
//            idcard.setId(id);
//            //加入身份证证件
//            int res2 = certificateMybatisDao.addCert(idcard);
//            if (res2 != 1) {
//                throw new Exception("添加身份证信息失败");
//            }
//            Image idcardImage = idcard.getCert();
//            String imageId = StringUtil.generate32uuid();
//            idcardImage.setImageId(imageId);
//            //加入证件图片
//            int res3 = imageMybatisDao.addImage(idcardImage);
//            if (res3 != 1) {
//                throw new Exception("添加身份证图片失败");
//            }
//        }
//
//    }
}
