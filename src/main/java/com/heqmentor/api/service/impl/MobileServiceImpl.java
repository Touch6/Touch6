package com.heqmentor.api.service.impl;

import com.heqmentor.api.service.MobileService;
import com.heqmentor.dao.repository.mybatis.MobileCodeMybatisDao;
import com.heqmentor.dao.repository.mybatis.UserMybatisDao;
import com.heqmentor.enums.MobileVerifyResult;
import com.heqmentor.po.entity.MobileCode;
import com.heqmentor.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * ============================================================================		
 * = COPYRIGHT		
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION		
 *   This software is supplied under the terms of a license agreement or		
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied		
 *   or disclosed except in accordance with the terms in that agreement.		
 *      Copyright (C) 2017-? PAX Technology, Inc. All rights reserved.		
 * Description: // Detail description about the function of this module,		
 *             // interfaces with the other modules, and dependencies. 		
 * Revision History:		
 * Date	                 Author	                  Action
 * 2017/2/23  	         zhuxl@paxsz.com        Create/Add/Modify/Delete
 * ============================================================================		
 */
@SuppressWarnings("ALL")
@Service
public class MobileServiceImpl implements MobileService {
    private static final Logger logger = LoggerFactory.getLogger(MobileServiceImpl.class);

    @Autowired
    UserMybatisDao userMybatisDao;
    @Autowired
    MobileCodeMybatisDao mobileCodeMybatisDao;

    @Override
    public void checkMobile(String mobile) throws Exception {
        String mbl = userMybatisDao.checkMobile(mobile);
        if (StringUtils.isNotBlank(mbl)) {
            throw new Exception("该手机号码已被注册");
        }
    }

    @Override
    @Transactional
    public String generateMobileCode(String mobile) throws Exception {
        String code = StringUtil.generate6MobileCode();
        MobileCode mobileCode = mobileCodeMybatisDao.findByMobile(mobile);
        if (mobileCode == null) {
            mobileCode = new MobileCode();
            mobileCode.setId(StringUtil.generate32uuid());
            mobileCode.setMobile(mobile);
            mobileCode.setPresCode(code);
            //insert mobileCode
            int res1 = mobileCodeMybatisDao.insertMobileCode(mobileCode);
            if (res1 != 1) {
                throw new Exception("保存手机验证码失败");
            }
        } else {
            mobileCode.setPrevCode(mobileCode.getPresCode());
            mobileCode.setPresCode(code);
            //update mobileCode
            int res2 = mobileCodeMybatisDao.updateMobileCode(mobileCode);
            if (res2 != 1) {
                throw new Exception("修改手机验证码失败");
            }
        }
        return code;
    }

    @Override
    @Transactional
    public void verifyMobileCode(String mobile, String code) throws Exception {
        MobileCode mobileCode = mobileCodeMybatisDao.findByMobile(mobile);
        if (mobileCode == null) {
            throw new Exception("手机号码错误");
        }
        if (mobileCode.getPrevCode().equals(code)) {
            //equals
            mobileCode.setVerifyResult(MobileVerifyResult.SUCCESS);
            //update mobileCode
            int res1 = mobileCodeMybatisDao.updateMobileCode(mobileCode);
            if (res1 != 1) {
                throw new Exception("修改验证结果失败");
            }
        } else {
            throw new Exception("手机验证码不正确");
        }
    }
}
