package com.touch6.business.api.service;

import com.touch6.business.dto.ToutiaoDto;
import com.touch6.commons.PageObject;

import java.util.List;

/**
 * Created by LONG on 2017/3/22.
 */
public interface ToutiaoService {
    /** 拉取头条更新
     * @return
     */
    PageObject<ToutiaoDto> listToutiao(int pageNo, int pageSize);
}
