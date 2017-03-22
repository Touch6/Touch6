package com.knowincloud.dao.repository.mybatis;

import com.knowincloud.dao.repository.mybatis.common.MyBatisRepository;
import com.knowincloud.po.entity.news.Toutiao;

import java.util.List;

/**
 * Created by LONG on 2017/3/22.
 */
@MyBatisRepository
public interface ToutiaoMybatisDao {
    int insertToutiaoInBatch(List<Toutiao> toutiaos);
}
