package com.knowincloud.dao.repository.mybatis;

import com.knowincloud.dao.repository.mybatis.common.MyBatisRepository;
import com.knowincloud.po.entity.Certificate;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface CertificateMybatisDao {

    int addCert(Certificate cert);
}
