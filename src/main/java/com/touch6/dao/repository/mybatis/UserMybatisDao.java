package com.touch6.dao.repository.mybatis;


import com.touch6.dao.repository.mybatis.common.MyBatisRepository;
import com.touch6.po.entity.User;

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

    User findByUid(String uid);

    int checkIsRegisteredByPhone(String phone);
}
