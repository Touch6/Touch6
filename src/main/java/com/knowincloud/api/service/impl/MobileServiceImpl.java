package com.knowincloud.api.service.impl;

import com.knowincloud.api.service.MobileService;
import com.knowincloud.constant.SmsGatewayConstant;
import com.knowincloud.core.exception.CoreException;
import com.knowincloud.core.exception.ECodeUtil;
import com.knowincloud.core.exception.error.constant.MobileErrorConstant;
import com.knowincloud.dao.repository.mybatis.MobileCodeMybatisDao;
import com.knowincloud.dao.repository.mybatis.UserMybatisDao;
import com.knowincloud.enums.MobileVerifyResult;
import com.knowincloud.enums.SmsGatewayInterface;
import com.knowincloud.po.entity.MobileCode;
import com.knowincloud.sm.gateway.KnowincloudSmsUtil;
import com.knowincloud.util.DateUtil;
import com.knowincloud.util.PropertiesUtil;
import com.knowincloud.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    public void generateMobileCode(String mobile) throws CoreException {
        String code = StringUtil.generate6MobileCode();
        Date now = DateUtil.nowTime();
        String selectedGateway = PropertiesUtil.getValue(SmsGatewayConstant.SMS_GATEWAY_PROPERTIES_FILENAME, SmsGatewayConstant.SMS_GATEWAY_SELECTED);
        SmsGatewayInterface gateway = SmsGatewayInterface.valueOf(selectedGateway);

        MobileCode mobileCode = mobileCodeMybatisDao.findByMobile(mobile);
        if (mobileCode == null) {
            //该手机号未生成过验证码
            mobileCode = new MobileCode();
            mobileCode.setId(StringUtil.generate32uuid());
            mobileCode.setMobile(mobile);
            mobileCode.setPresCode(code);
            mobileCode.setTimes(1);
            mobileCode.setVerifyTimes(0);
            Date time = DateUtil.nowTime();
            mobileCode.setPresTime(time);
            //insert mobileCode
            mobileCodeMybatisDao.insertMobileCode(mobileCode);
        } else {
            //该手机号生成过验证码，再次生成
            if (1 * 60 * 1000 > (now.getTime() - mobileCode.getPresTime().getTime())) {
                //发送验证码1分钟只能点击发送1次；
                throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_TOO_FREQUENT_60));
            }
            //累加次数
            mobileCode.setTimes(mobileCode.getTimes() + 1);

            mobileCode.setPrevTime(mobileCode.getPresTime());
            mobileCode.setPresTime(now);
            mobileCode.setPrevCode(mobileCode.getPresCode());
            mobileCode.setPresCode(code);
            mobileCode.setVerifyTimes(0);
            mobileCode.setPrevVerifyResult(mobileCode.getPresVerifyResult());
            //update mobileCode
            mobileCodeMybatisDao.updateMobileCode(mobileCode);
        }logger.info("向手机号：[{}]发送验证码：[{}]",mobile,code);
        KnowincloudSmsUtil.sendSmsCode(gateway, mobile, code);
    }

    @Override
    @Transactional
    public void verifyMobileCode(String mobile, String code) throws CoreException {
        MobileCode mobileCode = mobileCodeMybatisDao.findByMobile(mobile);
        if (mobileCode == null) {
            //手机号错误
            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_INCORRECT));
        }
        //判定时间是否在expired分钟内
        Date now = DateUtil.nowTime();
        int expired = Integer.valueOf(PropertiesUtil.getValue(SmsGatewayConstant.SMS_GATEWAY_PROPERTIES_FILENAME, SmsGatewayConstant.SMS_CODE_EXPIRED));
        if (expired * 60 * 1000 < (now.getTime() - mobileCode.getPresTime().getTime())) {
            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_EXPIRED));
        }
        if (mobileCode.getPresCode().equals(code)) {
            //equals
            //只允许验证1次,验证1次后失效
            if (mobileCode.getVerifyTimes() == 1) {
                throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_ONLY_VERIFY_ONE_TIMES));
            }
            mobileCode.setVerifyTimes(1);
            mobileCode.setPrevVerifyResult(mobileCode.getPresVerifyResult());
            mobileCode.setPresVerifyResult(MobileVerifyResult.SUCCESS);
            //update mobileCode
            mobileCodeMybatisDao.updateMobileCode(mobileCode);
        } else {
            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_INCORRECT));
        }
    }
}
