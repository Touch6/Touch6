package com.heqmentor.api.service.impl;


import com.heqmentor.api.service.UserService;
import com.heqmentor.dao.repository.mybatis.AuthMybatisDao;
import com.heqmentor.dao.repository.mybatis.CertificateMybatisDao;
import com.heqmentor.dao.repository.mybatis.ImageMybatisDao;
import com.heqmentor.dao.repository.mybatis.UserMybatisDao;
import com.heqmentor.dto.entity.RegisterDto;
import com.heqmentor.dto.entity.UserDto;
import com.heqmentor.po.entity.Auth;
import com.heqmentor.po.entity.Certificate;
import com.heqmentor.po.entity.Image;
import com.heqmentor.po.entity.User;
import com.heqmentor.util.PasswordEncryptionUtil;
import com.heqmentor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.mapper.BeanMapper;

import javax.validation.Validator;


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
    public void register(RegisterDto registerDto) throws Exception {
        BeanValidators.validateWithException(validator, registerDto);

        User user = new User();
        String uid = StringUtil.generate32uuid();
        user.setUid(uid);
        user.setMobile(registerDto.getMobile());
        //insert user
        int res1=userMybatisDao.register(user);
        if(res1!=1){
            throw new Exception("注册失败");
        }
        Auth auth = new Auth();
        String authId = StringUtil.generate32uuid();
        auth.setId(authId);
        auth.setUid(uid);
        auth.setLoginName(registerDto.getLoginName());
        String salt = StringUtil.generate32uuid();
        auth.setSalt(salt);
        auth.setPassword(PasswordEncryptionUtil.getEncryptedPassword(registerDto.getPassword(), salt));
        //insert auth
        int res2=authMybatisDao.insertAuth(auth);
        if(res2!=1){
            throw new Exception("添加登录信息失败");
        }
    }

    @Override
    @Transactional
    public void addUser(UserDto userDto) throws Exception {
        User user = BeanMapper.map(userDto, User.class);
        String uid = StringUtil.generate32uuid();
        user.setUid(uid);
        //加入用户信息
        int res1 = userMybatisDao.updateUser(user);
        if (res1 != 1) {
            throw new Exception("添加用户失败");
        }

        if (user.getIdcard() != null) {
            Certificate idcard = user.getIdcard();
            String id = StringUtil.generate32uuid();
            idcard.setId(id);
            //加入身份证证件
            int res2 = certificateMybatisDao.addCert(idcard);
            if (res2 != 1) {
                throw new Exception("添加身份证信息失败");
            }
            Image idcardImage = idcard.getCert();
            String imageId = StringUtil.generate32uuid();
            idcardImage.setImageId(imageId);
            //加入证件图片
            int res3 = imageMybatisDao.addImage(idcardImage);
            if (res3 != 1) {
                throw new Exception("添加身份证图片失败");
            }
        }

    }
}
