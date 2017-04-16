package com.touch6.business.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.AreaService;
import com.touch6.business.dto.init.area.*;
import com.touch6.business.entity.init.area.*;
import com.touch6.business.mybatis.init.area.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.mapper.BeanMapper;

import java.util.List;

/**
 * Created by LONG on 2017/4/17.
 */
@SuppressWarnings("ALL")
@Service
public class AreaServiceImpl implements AreaService {
    private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private ProvinceMybatisDao provinceMybatisDao;
    @Autowired
    private CityMybatisDao cityMybatisDao;
    @Autowired
    private DistrictMybatisDao districtMybatisDao;
    @Autowired
    private TownMybatisDao townMybatisDao;
    @Autowired
    private VillageMybatisDao villageMybatisDao;

    @Override
    public List<ProvinceDto> findAllProvinces() {
        List<Province> provinces = provinceMybatisDao.findAllProvinces();
        logger.info("查询出省份列表:[{}]", JSONArray.toJSONString(provinces));
        return BeanMapper.mapList(provinces, ProvinceDto.class);
    }

    @Override
    public List<CityDto> findAllCities(String provinceCode) {
        List<City> cities = cityMybatisDao.findCitiesByProvinceCode(provinceCode);
        logger.info("查询出省份:[{}]的城市列表:[{}]", provinceCode, JSONArray.toJSONString(cities));
        return BeanMapper.mapList(cities, CityDto.class);
    }

    @Override
    public List<DistrictDto> findAllDistricts(String cityId) {
        List<District> districts = districtMybatisDao.findDistrictsByCityCode(cityId);
        logger.info("查询出城市:[{}]的区县列表:[{}]", cityId, JSONArray.toJSONString(districts));
        return BeanMapper.mapList(districts, DistrictDto.class);
    }

    @Override
    public List<TownDto> findAllTowns(String districtCode) {
        List<Town> towns = townMybatisDao.findTownsByDistrictCode(districtCode);
        logger.info("查询出区县:[{}]的城镇列表:[{}]", districtCode, JSONArray.toJSONString(towns));
        return BeanMapper.mapList(towns, TownDto.class);
    }

    @Override
    public List<VillageDto> findAllVillages(String townId) {
        List<Village> villages = villageMybatisDao.findVillagesByTownCode(townId);
        logger.info("查询出城镇:[{}]的乡村列表:[{}]", townId, JSONArray.toJSONString(villages));
        return BeanMapper.mapList(villages, VillageDto.class);
    }
}
