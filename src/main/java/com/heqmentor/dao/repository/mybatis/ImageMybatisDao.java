package com.heqmentor.dao.repository.mybatis;

import com.heqmentor.dao.repository.mybatis.common.MyBatisRepository;
import com.heqmentor.po.entity.Image;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface ImageMybatisDao {
    int addImage(Image image);
}
