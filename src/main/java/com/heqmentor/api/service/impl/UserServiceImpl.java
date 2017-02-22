package com.heqmentor.api.service.impl;


import com.heqmentor.api.service.UserService;
import com.heqmentor.dao.repository.mybatis.UserMybatisDao;
import com.heqmentor.dto.entity.UserDto;
import com.heqmentor.po.entity.Certificate;
import com.heqmentor.po.entity.Image;
import com.heqmentor.po.entity.User;
import com.heqmentor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springside.modules.mapper.BeanMapper;

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
    private Validator validator;

    @Override
    @Transactional
    public void addUser(UserDto userDto) throws Exception {
        User user = BeanMapper.map(userDto, User.class);
        String uid = StringUtil.generate32uuid();
        user.setUid(uid);
        int result = userMybatisDao.addUser(user);
        if (result != 1) {
            throw new Exception("添加用户失败");
        }

        if (user.getIdcard() != null) {
            Certificate idcard = user.getIdcard();
            String id = StringUtil.generate32uuid();
            idcard.setId(id);

            Image idcardImage=idcard.getCert();
            String imageId=StringUtil.generate32uuid();
            idcardImage.setImageId(imageId);
        }

    }
}
