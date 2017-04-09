package com.touch6.business.api.service;

import com.touch6.business.dto.init.area.*;
import com.touch6.business.entity.init.area.*;

import java.util.List;

/**
 * Created by LONG on 2017/4/9.
 */
public interface RegionService {
    /**
     * 列出省份列表
     *
     * @return
     */
    List<ProvinceDto> findProvinces();

    /**
     * 列出指定省份的所有城市列表
     *
     * @param provinceCode
     * @return
     */
    List<CityDto> findCities(String provinceCode);

    /**
     * 列出指定市下所有区县
     *
     * @param cityCode
     * @return
     */
    List<DistrictDto> findDistricts(String cityCode);

    /**
     * 列出指定区县下所有乡镇
     *
     * @param districtCode
     * @return
     */
    List<TownDto> findTowns(String districtCode);

    /**
     * 列出指定乡镇下所有区委
     *
     * @param townCode
     * @return
     */
    List<VillageDto> findVillages(String townCode);
}
