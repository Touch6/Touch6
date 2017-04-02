package com.touch6.sm.gateway;

import com.alibaba.fastjson.JSONObject;
import com.touch6.constant.SmsGatewayConstant;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.error.constant.PhoneErrorConstant;
import com.touch6.core.exception.error.constant.SystemErrorConstant;
import com.touch6.business.enums.SmsGatewayInterface;
import com.touch6.sm.gateway.aliyun.AliyunSmsGateway;
import com.touch6.sm.gateway.sms253.HttpSender;
import com.touch6.sm.gateway.webchinese.Webchinese;
import com.touch6.utils.PropertiesUtil;
import com.touch6.utils.ReplaceUtil;

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
 * 2017/2/24  	         zhuxl@paxsz.com        Create/Add/Modify/Delete
 * ============================================================================		
 */
public class Touch6SmsUtil {
    public static void sendSmsCode(SmsGatewayInterface gateway, String phone, String code) throws CoreException {
        switch (gateway) {
            case SMS253:
                sendSmsBySms253(phone, code);
                break;
            case WEBCHINESE:
                sendSmsByWebchinese(phone, code);
                break;
            case ALIYUN:
                sendSmsByAliyun(phone, code);
                break;
            default:
                throw new CoreException(ECodeUtil.getCommError(PhoneErrorConstant.PHONE_CODE_SMS_GATEWAY_ERROR));
        }
    }

    public static void sendSmsBySms253(String phone, String code) throws CoreException {
        String fileName = SmsGatewayConstant.SMS_GATEWAY_PROPERTIES_FILENAME;

        String url = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_253_URL);// 应用地址
        String un = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_253_UN);// 账号
        String pw = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_253_PW);// 密码
        String expired = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_CODE_EXPIRED);// 过期时间
        String msgTemplate = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_253_MSG);// 短信内容
        String msg = ReplaceUtil.replaceAll(msgTemplate, "\\{\\}", new String[]{code, expired});
        String rd = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_253_RD);// 是否需要状态报告，需要1，不需要0
        String ex = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_253_EX);// 扩展码
        try {
            String returnString = HttpSender.batchSend(url, un, pw, phone, msg, rd, ex);
            System.out.println(returnString);
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }
    }

    public static void sendSmsByWebchinese(String phone, String code) throws CoreException {
        String fileName = SmsGatewayConstant.SMS_GATEWAY_PROPERTIES_FILENAME;

        String url = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_WEBCHINESE_URL);
        String uid = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_WEBCHINESE_UID);
        String key = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_WEBCHINESE_KEY);
        String expired = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_CODE_EXPIRED);// 过期时间
        String msgTemplate = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_WEBCHINESE_SMSTEXT);
        String msg = ReplaceUtil.replaceAll(msgTemplate, "\\{\\}", new String[]{code, expired});
        String contentType = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_WEBCHINESE_CONTENT_TYPE);
        String charset = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_WEBCHINESE_CHARSET);
        try {
            String returnString = Webchinese.batchSend(url, uid, key, phone, msg, contentType, charset);
            System.out.println(returnString);
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }
    }

    public static void sendSmsByAliyun(String phone, String code) throws CoreException {
        String fileName = SmsGatewayConstant.SMS_GATEWAY_PROPERTIES_FILENAME;

        String url = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_URL);
        String path = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_PATH);
        String method = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_METHOD);
        String expired = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_CODE_EXPIRED);
        String auth = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_AUTH);
        String signName = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_SIGNNAME);
        String msgTemplate = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_SMSTEMPLATE);
        JSONObject vars = new JSONObject();
        vars.put("code", code);
        vars.put("time", expired);
        boolean res = AliyunSmsGateway.batchSend(url, path, method, auth, vars.toJSONString(), phone, signName, msgTemplate);
        if (!res) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }
}
