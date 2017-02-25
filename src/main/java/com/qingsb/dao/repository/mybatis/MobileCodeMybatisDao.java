package com.qingsb.dao.repository.mybatis;


import com.qingsb.dao.repository.mybatis.common.MyBatisRepository;
import com.qingsb.po.entity.MobileCode;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface MobileCodeMybatisDao {
    MobileCode findByMobile(String mobile);

    int insertMobileCode(MobileCode mobileCode);

    int updateMobileCode(MobileCode mobileCode);

}
