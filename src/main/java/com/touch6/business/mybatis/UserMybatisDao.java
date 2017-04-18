package com.touch6.business.mybatis;


import com.touch6.business.mybatis.common.MyBatisRepository;
import com.touch6.business.entity.User;

import java.util.Map;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface UserMybatisDao {
    int register(User user);

    int updateUser(User user);

    String checkPhone(String phone);

    int perfectUserInfo(Map params);

    User findByToken(String uid);

    int checkIsRegisteredByPhone(String phone);

    User findByUserId(Long userId);
}
