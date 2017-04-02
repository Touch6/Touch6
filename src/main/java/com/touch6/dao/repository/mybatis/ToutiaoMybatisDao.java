package com.touch6.dao.repository.mybatis;

import com.touch6.dao.repository.mybatis.common.MyBatisRepository;
import com.touch6.po.entity.news.Toutiao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/3/22.
 */
@MyBatisRepository
public interface ToutiaoMybatisDao {
    int insertToutiaoInBatch(List<Toutiao> toutiaos);

    List<Toutiao> overview(Map params);
}
