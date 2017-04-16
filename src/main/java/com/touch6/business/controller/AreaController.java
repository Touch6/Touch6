package com.touch6.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.ApprovalService;
import com.touch6.business.api.service.AreaService;
import com.touch6.business.dto.common.ApprovalDto;
import com.touch6.business.dto.init.area.*;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/api/v1/area")
public class AreaController {
    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/provinces", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity provinces() {
        try {
            logger.info("查询省份");
            List<ProvinceDto> provinceDtos = areaService.findAllProvinces();
            Success ok = new Success(200, provinceDtos, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/province/{provinceCode}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity cities(@PathVariable("provinceCode") String provinceCode) {
        try {
            logger.info("查询城市");
            List<CityDto> cityDtos = areaService.findAllCities(provinceCode);
            Success ok = new Success(200, cityDtos, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/province/city/{cityCode}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity districts(@PathVariable("cityCode") String cityCode) {
        try {
            logger.info("查询区县");
            List<DistrictDto> districtDtos = areaService.findAllDistricts(cityCode);
            Success ok = new Success(200, districtDtos, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/province/city/district/{districtCode}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity towns(@PathVariable("districtCode") String districtCode) {
        try {
            logger.info("查询城镇");
            List<TownDto> townDtos = areaService.findAllTowns(districtCode);
            Success ok = new Success(200, townDtos, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/province/city/district/town/{townCode}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity villages(@PathVariable("townCode") String townCode) {
        try {
            logger.info("查询乡村");
            List<VillageDto> villageDtos = areaService.findAllVillages(townCode);
            Success ok = new Success(200, villageDtos, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
