package com.touch6.business.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.ToutiaoService;
import com.touch6.business.mybatis.ToutiaoMybatisDao;
import com.touch6.business.dto.ToutiaoDto;
import com.touch6.business.entity.Toutiao;
import com.touch6.commons.PageObject;
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
    public PageObject<ToutiaoDto> listToutiao(int pageNo, int pageSize) {
        logger.info("拉取更新头条pageNo:[{}],pageSize:[{}]", pageNo, pageSize);
        PageHelper.startPage(pageNo, pageSize, true);//查询出总数

        List<Toutiao> toutiaos = toutiaoMybatisDao.findAll();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<Toutiao> pageInfo = new PageInfo<Toutiao>(toutiaos);

        List<ToutiaoDto> toutiaoDtos = BeanMapper.mapList(pageInfo.getList(), ToutiaoDto.class);
        PageObject<ToutiaoDto> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(toutiaoDtos);

        return pageObject;
    }
}
