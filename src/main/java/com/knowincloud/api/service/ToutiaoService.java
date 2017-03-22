package com.knowincloud.api.service;

import com.knowincloud.dto.entity.news.ToutiaoDto;

import java.util.List;

/**
 * Created by LONG on 2017/3/22.
 */
public interface ToutiaoService {
    /** 拉取头条更新
     * @return
     */
    List<ToutiaoDto> listToutiao();
}
