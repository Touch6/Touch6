package com.knowincloud.dao.repository.mybatis;

import com.knowincloud.dao.repository.mybatis.common.MyBatisRepository;
import com.knowincloud.po.entity.Image;

/**
 * Created by LONG on 2017/2/22.
 */
@MyBatisRepository
public interface ImageMybatisDao {
    int addImage(Image image);
}
