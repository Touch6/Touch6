package com.heqmentor.api.service.impl;

import com.heqmentor.api.service.MobileService;
import com.heqmentor.constant.SmsGatewayConstant;
import com.heqmentor.core.exception.CoreException;
import com.heqmentor.core.exception.ECodeUtil;
import com.heqmentor.core.exception.error.constant.MobileErrorConstant;
import com.heqmentor.core.exception.error.constant.SystemErrorConstant;
import com.heqmentor.dao.repository.mybatis.MobileCodeMybatisDao;
import com.heqmentor.dao.repository.mybatis.UserMybatisDao;
import com.heqmentor.enums.MobileVerifyResult;
import com.heqmentor.enums.SmsGatewayInterface;
import com.heqmentor.po.entity.MobileCode;
import com.heqmentor.sm.gateway.SmsGatewayUtil;
import com.heqmentor.util.DateUtil;
import com.heqmentor.util.PropertiesUtil;
import com.heqmentor.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
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
    public String generateMobileCode(String mobile) throws CoreException {
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
            Date time = DateUtil.nowTime();
            mobileCode.setPrevTime(time);
            mobileCode.setPresTime(time);
            //insert mobileCode
            mobileCodeMybatisDao.insertMobileCode(mobileCode);
        } else {
            //该手机号生成过验证码，再次生成
            switch (gateway) {
                case SMS253:
                    //一个手机号码，一天只能接受十条短信。超过十条就接收不到了
                    if (mobileCode.getTimes() >= 10) {
                        DateTime nowTime = new DateTime(now);
                        DateTime presTime = new DateTime(mobileCode.getPresTime());
                        int days = Days.daysBetween(nowTime, presTime).getDays();
                        if (days == 0) {
                            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_TIMES_TOO_MANY));
                        } else {
                            //次日重置次数
                            mobileCode.setTimes(1);
                        }
                    } else {
                        //不足10次，累加次数
                        mobileCode.setTimes(mobileCode.getTimes() + 1);
                    }
                    break;
                case WEBCHINESE:
                    //发送验证码1分钟只能点击发送1次；
                    //相同IP手机号码1天最多提交20次；
                    //验证码短信单个手机号码30分钟最多提交10次；
                    if (1 * 60 * 1000 > (now.getTime() - mobileCode.getPresTime().getTime())) {
                        throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_TOO_FREQUENT_60));
                    }
                    if (mobileCode.getTimes() >= 10) {
                        if(mobileCode.getTimes()>=20){
                            //// TODO: 2017/2/24  
                        }
                        DateTime nowTime = new DateTime(now);
                        DateTime presTime = new DateTime(mobileCode.getPresTime());
                        int days = Days.daysBetween(nowTime, presTime).getDays();
                        if (days == 0) {
                            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_TIMES_TOO_MANY));
                        } else {
                            //次日重置次数
                            mobileCode.setTimes(1);
                        }
                    } else {
                        //不足10次，累加次数
                        mobileCode.setTimes(mobileCode.getTimes() + 1);
                    }
                    break;
                default:
                    logger.info("参数错误:[{}]", gateway);
                    throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
            }

            if (mobileCode.getTimes() == 6) {
                //判定当前时间距离上一次时间间隔，1小时内最多获取6次验证码
                Date pres = mobileCode.getPresTime();
                long ms = now.getTime() - pres.getTime();
                if (ms < 60 * 60 * 1000) {
                    //时间间隔小于1小时，抛出异常
                    throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_TIMES_TOO_MANY));
                } else {
                    //时间间隔大于1小时，重置次数
                    mobileCode.setTimes(1);
                }
            } else {
                //次数小于6次，正常产生验证码,累加次数
                mobileCode.setTimes(mobileCode.getTimes() + 1);
            }

            mobileCode.setPrevTime(mobileCode.getPresTime());
            mobileCode.setPresTime(now);
            mobileCode.setPrevCode(mobileCode.getPresCode());
            mobileCode.setPresCode(code);
            mobileCode.setPrevVerifyResult(mobileCode.getPresVerifyResult());
            mobileCode.setPresVerifyResult(null);
            //update mobileCode
            mobileCodeMybatisDao.updateMobileCode(mobileCode);
        }
        SmsGatewayUtil.sendSmsCode(gateway, mobile, code);
        return code;
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
            mobileCode.setPrevVerifyResult(mobileCode.getPresVerifyResult());
            mobileCode.setPresVerifyResult(MobileVerifyResult.SUCCESS);
            //update mobileCode
            mobileCodeMybatisDao.updateMobileCode(mobileCode);
        } else {
            throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_INCORRECT));
        }
    }
}
