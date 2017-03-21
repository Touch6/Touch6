package com.knowincloud.dao.repository.mybatis;


import com.knowincloud.dao.repository.mybatis.common.MyBatisRepository;
import com.knowincloud.po.entity.User;

import java.util.Map;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface UserMybatisDao {
    int register(User user);

    int updateUser(User user);

    String checkMobile(String mobile);

    int perfectUserInfo(Map params);

    User findByUid(String uid);

    int checkIsRegisteredByMobile(String mobile);
}
