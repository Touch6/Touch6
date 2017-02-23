package com.heqmentor.dao.repository.mybatis;


import com.heqmentor.dao.repository.mybatis.common.MyBatisRepository;
import com.heqmentor.po.entity.MobileCode;
import com.heqmentor.po.entity.User;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface MobileCodeMybatisDao {
    MobileCode findByMobile(String mobile);

    int insertMobileCode(MobileCode mobileCode);

    int updateMobileCode(MobileCode mobileCode);

}
