package com.heqmentor.dao.repository.mybatis;

import com.heqmentor.dao.repository.mybatis.common.MyBatisRepository;
import com.heqmentor.po.entity.Certificate;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface CertificateMybatisDao {

    int addCert(Certificate cert);
}
