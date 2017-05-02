package com.touch6.business.api.service.impl.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.touch6.business.api.service.system.UserRoleService;
import com.touch6.business.entity.User;
import com.touch6.business.entity.system.Role;
import com.touch6.business.entity.system.UserRole;
import com.touch6.business.mybatis.UserMybatisDao;
import com.touch6.business.mybatis.system.*;
import com.touch6.commons.PageObject;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.error.constant.CommonErrorConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.mapper.BeanMapper;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LONG on 2017/4/18.
 */
@SuppressWarnings("ALL")
@Service
public class UserRoleServiceImpl implements UserRoleService {
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
    @Transactional
    public void assignUserRole(JSONObject userrole) {
        Long userId = userrole.getLong("userId");
        List<Long> roleIds = Lists.newArrayList();
        JSONArray roleList = userrole.getJSONArray("roleList");
        if (roleList.size() > 0) {
            for (int i = 0; i < roleList.size(); i++) {
                JSONObject obj = roleList.getJSONObject(i);
                boolean checked = obj.getBoolean("checked");
                Long roleId = obj.getLong("roleId");
                if (checked) {
                    roleIds.add(roleId);
                }
            }
        }
        //先删除原来的配置
        int deleted = userRoleMybatisDao.deleteUserRoleByUserId(userId);
        logger.info("删除原配置:[{}]", deleted);
        //然后插入新的配置
        Map params = new HashMap();
        params.put("userId", userId);
        params.put("roleIds", roleIds);
        int inserted = userRoleMybatisDao.insertUserRoleInBatch(params);
        logger.info("新插入配置:[{}]", inserted);
    }

    @Override
    public JSONObject findAllUserroleByUserId(Long userId) {
        User user = userMybatisDao.findByUserId(userId);
        List<UserRole> userRoleList = userRoleMybatisDao.findAllUserroleByUserId(userId);
        JSONObject out = new JSONObject();
        out.put("userId", userId);
        out.put("userName", user.getName());
        List<JSONObject> list = Lists.newArrayList();
        if (userRoleList.size() > 0) {
            for (UserRole ur : userRoleList) {
                JSONObject obj = new JSONObject();
                obj.put("roleId", ur.getRoleId());
                obj.put("roleName", ur.getRoleName());
                if (ur.getUserId() == null) {
                    obj.put("checked", false);
                } else {
                    obj.put("checked", true);
                }
                list.add(obj);
            }
        }
        out.put("roleList", list);
        return out;
    }
}
