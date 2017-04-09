package com.touch6.business.mybatis.init.area;


import com.touch6.business.entity.init.area.District;
import com.touch6.business.entity.init.article.ArticleType;
import com.touch6.business.mybatis.common.MyBatisRepository;

import java.util.List;

/**
 * Created by zhuxl on 2015/5/20.
 */

@MyBatisRepository
public interface DistrictMybatisDao {
    List<District> findDistrictsByCityCode(String cityCode);
}
