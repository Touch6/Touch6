package com.heqmentor.dao.repository.mybatis;


import com.heqmentor.dao.repository.mybatis.common.MyBatisRepository;
import com.heqmentor.po.entity.User;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface UserMybatisDao {
    int register(User user);

    int updateUser(User user);

    String checkMobile(String mobile);
}
