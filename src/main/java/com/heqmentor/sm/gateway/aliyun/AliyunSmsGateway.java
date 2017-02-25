package com.heqmentor.sm.gateway.aliyun;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

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
 * 2017/2/25  	         zhuxl@paxsz.com        Create/Add/Modify/Delete
 * ============================================================================		
 */
public class AliyunSmsGateway {
    public static final void batchSend(String url, String path, String method, String auth, String vars, String mobiles, String signName, String smsTemplate) {
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", auth);
        Map<String, String> querys = new HashMap<>();
        querys.put("ParamString", vars);
        querys.put("RecNum", mobiles);
        querys.put("SignName", signName);
        querys.put("TemplateCode", smsTemplate);
        try {
            HttpResponse response = HttpUtils.doGet(url, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "http://sms.market.alicloudapi.com";
        String path = "/singleSendSms";
        String method = "GET";
        String auth = "APPCODE 0b4cc9f202234a81b22507209358c05f";
        String vars = "{\"code\":\"778899\",\"time\":\"15\"}";
        String mobiles = "13880298929";
        String signName = "情舒宝";
        String smsTemplate = "SMS_49260144";
        batchSend(host, path, method, auth, vars, mobiles, signName, smsTemplate);
    }
}
