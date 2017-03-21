package com.knowincloud.dao.repository.mybatis;


import com.knowincloud.dao.repository.mybatis.common.MyBatisRepository;
import com.knowincloud.po.entity.MobileCode;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface MobileCodeMybatisDao {
    MobileCode findByMobile(String mobile);

    int insertMobileCode(MobileCode mobileCode);

    int updateMobileCode(MobileCode mobileCode);

}
