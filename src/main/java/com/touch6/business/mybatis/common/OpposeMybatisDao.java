package com.touch6.business.mybatis.common;

import com.touch6.business.entity.common.Oppose;

/**
 * Created by PAX on 2017/4/13.
 */
@MyBatisRepository
public interface OpposeMybatisDao {
    int addOppose(Oppose oppose);
}
