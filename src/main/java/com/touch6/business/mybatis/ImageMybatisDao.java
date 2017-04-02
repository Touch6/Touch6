package com.touch6.business.mybatis;

import com.touch6.business.mybatis.common.MyBatisRepository;
import com.touch6.business.entity.Image;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface ImageMybatisDao {
    int addImage(Image image);
}
