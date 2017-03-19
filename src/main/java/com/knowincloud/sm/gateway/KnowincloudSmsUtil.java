package com.knowincloud.sm.gateway;

import com.alibaba.fastjson.JSONObject;
import com.knowincloud.constant.SmsGatewayConstant;
import com.knowincloud.core.exception.CoreException;
import com.knowincloud.core.exception.ECodeUtil;
import com.knowincloud.core.exception.error.constant.MobileErrorConstant;
import com.knowincloud.enums.SmsGatewayInterface;
import com.knowincloud.sm.gateway.aliyun.AliyunSmsGateway;
import com.knowincloud.sm.gateway.sms253.HttpSender;
import com.knowincloud.sm.gateway.webchinese.Webchinese;
import com.knowincloud.util.PropertiesUtil;
import com.knowincloud.util.ReplaceUtil;

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
public class KnowincloudSmsUtil {
    public static void sendSmsCode(SmsGatewayInterface gateway, String mobile, String code) throws CoreException {
        switch (gateway) {
            case SMS253:
                sendSmsBySms253(mobile, code);
                break;
            case WEBCHINESE:
                sendSmsByWebchinese(mobile, code);
                break;
            case ALIYUN:
                sendSmsByAliyun(mobile, code);
                break;
            default:
                throw new CoreException(ECodeUtil.getCommError(MobileErrorConstant.MOBILE_CODE_SMS_GATEWAY_ERROR));
        }
    }

    public static void sendSmsBySms253(String mobile, String code) throws CoreException {
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
            String returnString = HttpSender.batchSend(url, un, pw, mobile, msg, rd, ex);
            System.out.println(returnString);
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }
    }

    public static void sendSmsByWebchinese(String mobile, String code) throws CoreException {
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
            String returnString = Webchinese.batchSend(url, uid, key, mobile, msg, contentType, charset);
            System.out.println(returnString);
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }
    }

    public static void sendSmsByAliyun(String mobile, String code) throws CoreException {
        String fileName = SmsGatewayConstant.SMS_GATEWAY_PROPERTIES_FILENAME;

        String url = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_URL);
        String path = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_PATH);
        String method = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_METHOD);
        String expired = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_CODE_EXPIRED);
        String auth = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_AUTH);
        String signName = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_SIGNNAME);
        String msgTemplate = PropertiesUtil.getValue(fileName, SmsGatewayConstant.SMS_GATEWAY_ALIYUN_SMSTEMPLATE);
        try {
            JSONObject vars = new JSONObject();
            vars.put("code", code);
            vars.put("time", expired);
            String returnString = AliyunSmsGateway.batchSend(url, path, method, auth, vars.toJSONString(), mobile, signName, msgTemplate);
            System.out.println(returnString);
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }
    }
}
