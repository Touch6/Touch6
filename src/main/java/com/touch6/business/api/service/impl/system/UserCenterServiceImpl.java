package com.touch6.business.api.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touch6.business.api.service.system.UserCenterService;
import com.touch6.business.dto.UserDto;
import com.touch6.business.entity.User;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.business.mybatis.system.*;
import com.touch6.commons.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.mapper.BeanMapper;

import javax.validation.Validator;
import java.util.List;

/**
 * Created by PAX on 2017/4/24.
 */
@SuppressWarnings("ALL")
@Service
public class UserCenterServiceImpl implements UserCenterService{

    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
    @Autowired
    private RoleMybatisDao roleMybatisDao;
    @Autowired
    private AuthMybatisDao authMybatisDao;
    @Autowired
    private ModuleMybatisDao moduleMybatisDao;
    @Autowired
    private MenuMybatisDao menuMybatisDao;
    @Autowired
    private UserRoleMybatisDao userRoleMybatisDao;
    @Autowired
    private AuthRoleMybatisDao authRoleMybatisDao;
    @Autowired
    private AuthMenuMybatisDao authMenuMybatisDao;
    @Autowired
    private UserMybatisDao userMybatisDao;
    @Autowired
    private RouteMybatisDao routeMybatisDao;
    @Autowired
    private Validator validator;
    @Override
    public PageObject<UserDto> findAllUsers(int page, int pageSize) {
        logger.info("所有用户信息page:[{}],pageSize:[{}]", page, pageSize);
        PageHelper.startPage(page, pageSize, true);//查询出总数

        List<User> users = userMybatisDao.findAllUsers();
        //分页实现
        //或者使用PageInfo类（下面的例子有介绍）
        PageInfo<User> pageInfo = new PageInfo<User>(users);

        List<UserDto> userDtos = BeanMapper.mapList(pageInfo.getList(), UserDto.class);
        PageObject<UserDto> pageObject = BeanMapper.map(pageInfo, PageObject.class);
        pageObject.setList(userDtos);

        return pageObject;
    }
}
