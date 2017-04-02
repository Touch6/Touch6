package com.touch6.business.mybatis;


import com.touch6.business.mybatis.common.MyBatisRepository;
import com.touch6.business.entity.PhoneCode;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface PhoneCodeMybatisDao {
    PhoneCode findByPhone(String phone);

    int insertPhoneCode(PhoneCode phoneCode);

    int updatePhoneCode(PhoneCode phoneCode);

}
