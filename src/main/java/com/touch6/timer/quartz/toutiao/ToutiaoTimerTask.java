package com.touch6.timer.quartz.toutiao;

import com.alibaba.fastjson.JSONArray;
import com.touch6.dao.repository.mybatis.ToutiaoMybatisDao;
import com.touch6.po.entity.news.Toutiao;
import com.touch6.third.api.toutiao.KicToutiaoApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xuan.touch6@qq.com on 2017/3/28.
 */
@Service
public class ToutiaoTimerTask {
    private static final Logger logger = LoggerFactory.getLogger(ToutiaoTimerTask.class);
    @Autowired
    private ToutiaoMybatisDao toutiaoMybatisDao;

    public void updateToutiao() {
        logger.info("定时任务获取头条更新开始");
        JSONArray toutiao = KicToutiaoApi.pullNews();
        List<Toutiao> toutiaos = KicToutiaoApi.toToutiaoList(toutiao);
        int result = toutiaoMybatisDao.insertToutiaoInBatch(toutiaos);
        logger.info("更新条数:[{}]", result);
    }
}
