package com.touch6.timer.quartz.toutiao;

import com.alibaba.fastjson.JSONArray;
import com.touch6.business.mybatis.ToutiaoMybatisDao;
import com.touch6.business.entity.Toutiao;
import com.touch6.third.api.toutiao.KicToutiaoApi;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by xuan.touch6@qq.com on 2017/3/28.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ToutiaoTimerTask {
    private static final Logger logger = LoggerFactory.getLogger(ToutiaoTimerTask.class);
    @Autowired
    private ToutiaoMybatisDao toutiaoMybatisDao;

    @Transactional
    public void updateToutiao() {
        logger.info("定时任务获取头条更新开始");
        JSONArray toutiao = KicToutiaoApi.pullNews();
        List<Toutiao> toutiaos = KicToutiaoApi.toToutiaoList(toutiao);
        int result = toutiaoMybatisDao.insertToutiaoInBatch(toutiaos);
        logger.info("更新条数:[{}]", result);
    }

    @Transactional
    public void deleteToutiao() {
        logger.info("定时任务删除时间超过10天的头条新闻");
        DateTime now = new DateTime();
        DateTime before10 = now.minusDays(10);
        //删除时间早于before30的头条
        int deleted = toutiaoMybatisDao.deleteToutiaoBefore10(new Date(before10.getMillis()));
        logger.info("删除记录总数:[{}]", deleted);
    }

}
