package com.heqmentor.api.service.impl;

import com.heqmentor.api.service.MobileService;
import com.heqmentor.core.exception.CoreException;
import com.heqmentor.core.exception.ECodeUtil;
import com.heqmentor.core.exception.error.constant.MobileErrorConstant;
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
    public void checkMobile(String mobile) throws CoreException {
        String mbl = userMybatisDao.checkMobile(mobile);
        if (StringUtils.isNotBlank(mbl)) {
            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_ALREADY_REGISTERED));
        }
    }

    @Override
    @Transactional
    public String generateMobileCode(String mobile) throws CoreException {
        String code = StringUtil.generate6MobileCode();
        MobileCode mobileCode = mobileCodeMybatisDao.findByMobile(mobile);
        if (mobileCode == null) {
            mobileCode = new MobileCode();
            mobileCode.setId(StringUtil.generate32uuid());
            mobileCode.setMobile(mobile);
            mobileCode.setPresCode(code);
            //insert mobileCode
            mobileCodeMybatisDao.insertMobileCode(mobileCode);
        } else {
            mobileCode.setPrevCode(mobileCode.getPresCode());
            mobileCode.setPresCode(code);
            //update mobileCode
            mobileCodeMybatisDao.updateMobileCode(mobileCode);
        }
        return code;
    }

    @Override
    @Transactional
    public void verifyMobileCode(String mobile, String code) throws CoreException {
        MobileCode mobileCode = mobileCodeMybatisDao.findByMobile(mobile);
        if (mobileCode == null) {
            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_INCORRECT));
        }
        if (mobileCode.getPresCode().equals(code)) {
            //equals
            mobileCode.setVerifyResult(MobileVerifyResult.SUCCESS);
            //update mobileCode
            mobileCodeMybatisDao.updateMobileCode(mobileCode);
        } else {
            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_INCORRECT));
        }
    }
}
