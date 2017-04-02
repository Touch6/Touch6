package com.touch6.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.touch6.api.service.ToutiaoService;
import com.touch6.dao.repository.mybatis.ToutiaoMybatisDao;
import com.touch6.dto.entity.news.ToutiaoDto;
import com.touch6.po.entity.news.Toutiao;
import com.touch6.third.api.toutiao.KicToutiaoApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.mapper.BeanMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/3/22.
 */
@SuppressWarnings("ALL")
@Service
public class ToutiaoServiceImpl implements ToutiaoService {
    private static final Logger logger = LoggerFactory.getLogger(ToutiaoServiceImpl.class);

    @Autowired
    private ToutiaoMybatisDao toutiaoMybatisDao;

    @Override
    public List<ToutiaoDto> listToutiao(int pageNo, int pageSize) {
        logger.info("拉取更新头条pageNo:[{}],pageSize:[{}]", pageNo, pageSize);
        int offset = (pageNo-1) * pageSize;
        Map params = new HashMap();
        params.put("limit", pageSize);
        params.put("offset", offset);
        List<Toutiao> toutiaos = toutiaoMybatisDao.overview(params);
        return BeanMapper.mapList(toutiaos, ToutiaoDto.class);
    }
}
