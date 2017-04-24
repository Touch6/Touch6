package com.touch6.business.controller.system;

import com.touch6.business.api.service.system.UserCenterService;
import com.touch6.business.dto.UserDto;
import com.touch6.commons.PageObject;
import com.touch6.core.exception.CoreException;
import com.touch6.core.info.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhuxl@paxsz.com on 2016/7/27.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/system")
public class UserCenterController {
    private static final Logger logger = LoggerFactory.getLogger(UserCenterController.class);

    @Autowired
    private UserCenterService userCenterService;

    @RequestMapping(value = "pageable", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity pageUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            PageObject<UserDto> pageObject = userCenterService.findAllUsers(page, pageSize);
            Success ok = new Success(200, pageObject, "查询成功");
            return new ResponseEntity(ok, HttpStatus.OK);
        } catch (CoreException e) {
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        }
    }
}
