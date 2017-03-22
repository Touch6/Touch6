package com.knowincloud.controller;

import com.knowincloud.api.service.ToutiaoService;
import com.knowincloud.api.service.UserService;
import com.knowincloud.core.exception.CoreException;
import com.knowincloud.core.info.Success;
import com.knowincloud.dto.entity.UserDto;
import com.knowincloud.dto.entity.news.ToutiaoDto;
import com.knowincloud.params.LoginParam;
import com.knowincloud.params.PerfectInfoParam;
import com.knowincloud.params.RegisterParam;
import com.knowincloud.params.UniqueParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/api/v1/toutiao")
public class ToutiaoController {
    private static final Logger logger = LoggerFactory.getLogger(ToutiaoController.class);

    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(value = "news", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity pull() {
        try {
            List<ToutiaoDto> toutiaoDtos = toutiaoService.listToutiao();
            Success ok = new Success(200, toutiaoDtos, "更新成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
