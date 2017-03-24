package com.touch6.dao.repository.mybatis;

import com.touch6.dao.repository.mybatis.common.MyBatisRepository;
import com.touch6.po.entity.Certificate;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface CertificateMybatisDao {

    int addCert(Certificate cert);
}
