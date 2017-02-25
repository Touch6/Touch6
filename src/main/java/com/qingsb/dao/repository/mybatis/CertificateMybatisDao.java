package com.qingsb.dao.repository.mybatis;

import com.qingsb.dao.repository.mybatis.common.MyBatisRepository;
import com.qingsb.po.entity.Certificate;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface CertificateMybatisDao {

    int addCert(Certificate cert);
}
