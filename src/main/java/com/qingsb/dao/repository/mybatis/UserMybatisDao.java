package com.qingsb.dao.repository.mybatis;


import com.qingsb.dao.repository.mybatis.common.MyBatisRepository;
import com.qingsb.po.entity.User;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface UserMybatisDao {
    int register(User user);

    int updateUser(User user);

    String checkMobile(String mobile);
}
