package com.touch6.business.api.service.impl;

import com.touch6.business.api.service.RegionService;
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
 * Created by LONG on 2017/4/9.
 */
@SuppressWarnings("ALL")
@Service
public class RegionServiceImpl implements RegionService {

    private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);
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
    public List<ProvinceDto> findProvinces() {
        return BeanMapper.mapList(provinceMybatisDao.findAllProvinces(), ProvinceDto.class);
    }

    @Override
    public List<CityDto> findCities(String provinceCode) {
        return BeanMapper.mapList(cityMybatisDao.findCitiesByProvinceCode(provinceCode), CityDto.class);
    }

    @Override
    public List<DistrictDto> findDistricts(String cityCode) {
        return BeanMapper.mapList(districtMybatisDao.findDistrictsByCityCode(cityCode), DistrictDto.class);
    }

    @Override
    public List<TownDto> findTowns(String districtCode) {
        return BeanMapper.mapList(townMybatisDao.findTownsByDistrictCode(districtCode), TownDto.class);
    }

    @Override
    public List<VillageDto> findVillages(String townCode) {
        return BeanMapper.mapList(villageMybatisDao.findVillagesByTownCode(townCode), VillageDto.class);
    }
}
