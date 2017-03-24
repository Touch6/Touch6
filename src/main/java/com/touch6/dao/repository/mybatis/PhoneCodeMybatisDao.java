package com.touch6.dao.repository.mybatis;


import com.touch6.dao.repository.mybatis.common.MyBatisRepository;
import com.touch6.po.entity.PhoneCode;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface PhoneCodeMybatisDao {
    PhoneCode findByPhone(String phone);

    int insertPhoneCode(PhoneCode phoneCode);

    int updatePhoneCode(PhoneCode phoneCode);

}
