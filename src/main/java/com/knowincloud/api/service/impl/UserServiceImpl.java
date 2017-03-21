package com.knowincloud.api.service.impl;


import com.knowincloud.api.service.UserService;
import com.knowincloud.core.exception.CoreException;
import com.knowincloud.core.exception.ECodeUtil;
import com.knowincloud.core.exception.Error;
import com.knowincloud.core.exception.error.constant.*;
import com.knowincloud.dao.repository.mybatis.AuthMybatisDao;
import com.knowincloud.dao.repository.mybatis.CertificateMybatisDao;
import com.knowincloud.dao.repository.mybatis.ImageMybatisDao;
import com.knowincloud.dao.repository.mybatis.UserMybatisDao;
import com.knowincloud.dto.entity.UserDto;
import com.knowincloud.enums.UserInfo;
import com.knowincloud.params.LoginParam;
import com.knowincloud.params.PerfectInfoParam;
import com.knowincloud.params.RegisterParam;
import com.knowincloud.po.entity.Auth;
import com.knowincloud.po.entity.User;
import com.knowincloud.util.PasswordEncryptionUtil;
import com.knowincloud.util.StringUtil;
import com.knowincloud.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.mapper.BeanMapper;

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
        String error = ValidatorUtil.validate(validator, registerParam);
        if (error != null) {
            Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
            err.setDes(error);
            throw new CoreException(err);
        }
        //判定密码和确认密码是否一样
        if (!registerParam.getPassword().equals(registerParam.getConfirmPassword())) {
            throw new CoreException(ECodeUtil.getCommError(UserInfoErrorConstant.USER_INFO_PASSWORD_CONFIRM_ERROR));
        }
        //判定手机号是否已注册
        int count1=userMybatisDao.checkIsRegisteredByPhone(registerParam.getPhone());
        if(count1>0){
            throw new CoreException(ECodeUtil.getCommError(PhoneErrorConstant.PHONE_ALREADY_REGISTERED));
        }

        //判定登录名是否已注册
        int count2=authMybatisDao.checkIsRegisteredByLoginName(registerParam.getLoginName());
        if(count2>0){
            throw new CoreException(ECodeUtil.getCommError(AuthErrorConstant.AUTH_LOGIN_NAME_EXISTED));
        }

        User user = new User();
        String uid = StringUtil.generate32uuid();
        user.setUid(uid);
        user.setPhone(registerParam.getPhone());
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
    public UserDto login(LoginParam loginParam) throws CoreException {
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
        User user=userMybatisDao.findByUid(auth.getUid());
        return BeanMapper.map(user,UserDto.class);
    }

    @Override
    @Transactional
    public void perfectUserInfo(PerfectInfoParam perfectInfoParam) throws CoreException {
        try {
            String error = ValidatorUtil.validate(validator, perfectInfoParam);
            if (error != null) {
                Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
                err.setDes(error);
                throw new CoreException(err);
            }
            UserInfo infoType = UserInfo.valueOf(perfectInfoParam.getType());
            Map params = new HashMap();
            String column = infoType.name().toLowerCase();
            params.put("uid", "'" + perfectInfoParam.getUid() + "'");
            params.put("column", column);
            if (StringUtils.isBlank(perfectInfoParam.getValue())) {
                params.put("value", "null");
            } else {
                params.put("value", "'" + perfectInfoParam.getValue() + "'");
            }
            logger.info("即将完善的信息参数:[{}]", params.toString());
            userMybatisDao.perfectUserInfo(params);
        } catch (Exception e) {
            logger.info("完善信息异常，堆栈:", e);
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
    }

    @Override
    public UserDto getUserInfo(String uid) throws CoreException {
        User user=userMybatisDao.findByUid(uid);
        if(user==null){
            throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
        }
        UserDto userDto= BeanMapper.map(user,UserDto.class);
        return userDto;
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
