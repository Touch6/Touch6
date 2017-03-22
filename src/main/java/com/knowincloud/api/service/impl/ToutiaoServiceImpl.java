package com.knowincloud.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.knowincloud.api.service.ToutiaoService;
import com.knowincloud.dao.repository.mybatis.ToutiaoMybatisDao;
import com.knowincloud.dto.entity.news.ToutiaoDto;
import com.knowincloud.po.entity.news.Toutiao;
import com.knowincloud.third.api.toutiao.KicToutiaoApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.mapper.BeanMapper;

import java.util.List;

/**
 * Created by LONG on 2017/3/22.
 */
@SuppressWarnings("ALL")
@Service
public class ToutiaoServiceImpl implements ToutiaoService{
    private static final Logger logger= LoggerFactory.getLogger(ToutiaoServiceImpl.class);

    @Autowired
    private ToutiaoMybatisDao toutiaoMybatisDao;

    @Override
    public List<ToutiaoDto> listToutiao() {
        logger.info("拉取更新头条");
        JSONArray toutiao=KicToutiaoApi.pullNews();
        List<Toutiao> toutiaos=KicToutiaoApi.toToutiaoList(toutiao);
        int result=toutiaoMybatisDao.insertToutiaoInBatch(toutiaos);
        logger.info("插入数据库的条数:[{}]",result);
        return BeanMapper.mapList(toutiaos,ToutiaoDto.class);
    }
}
