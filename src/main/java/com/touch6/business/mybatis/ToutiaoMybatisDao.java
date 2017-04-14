package com.touch6.business.mybatis;

import com.touch6.business.mybatis.common.MyBatisRepository;
import com.touch6.business.entity.Toutiao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/3/22.
 */
@MyBatisRepository
public interface ToutiaoMybatisDao {
    int insertToutiaoInBatch(List<Toutiao> toutiaos);

    List<Toutiao> overview(Map params);

    int deleteToutiaoBefore10(Date date);

    List<Toutiao> findAll();

    void increaseApprovalAmount(String id);

    void increaseOpposeAmount(String id);
}
