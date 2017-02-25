package com.qingsb.dao.repository.mybatis;

import com.qingsb.dao.repository.mybatis.common.MyBatisRepository;
import com.qingsb.po.entity.Image;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface ImageMybatisDao {
    int addImage(Image image);
}
