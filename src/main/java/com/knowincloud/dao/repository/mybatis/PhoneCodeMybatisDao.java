package com.knowincloud.dao.repository.mybatis;


import com.knowincloud.dao.repository.mybatis.common.MyBatisRepository;
import com.knowincloud.po.entity.PhoneCode;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface PhoneCodeMybatisDao {
    PhoneCode findByPhone(String phone);

    int insertPhoneCode(PhoneCode phoneCode);

    int updatePhoneCode(PhoneCode phoneCode);

}
