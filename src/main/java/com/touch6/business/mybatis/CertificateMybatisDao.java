package com.touch6.business.mybatis;

import com.touch6.business.mybatis.common.MyBatisRepository;
import com.touch6.business.entity.Certificate;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface CertificateMybatisDao {

    int addCert(Certificate cert);
}
