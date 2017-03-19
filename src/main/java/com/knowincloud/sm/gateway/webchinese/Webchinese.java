package com.knowincloud.sm.gateway.webchinese;

import com.knowincloud.core.exception.CoreException;
import com.knowincloud.core.exception.ECodeUtil;
import com.knowincloud.core.exception.error.constant.SystemErrorConstant;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class Webchinese {
    private static final Logger logger = LoggerFactory.getLogger(Webchinese.class);

    public static String batchSend(String url, String uid, String key, String phone, String msg,
                                   String contentType, String charset) throws CoreException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.addRequestHeader("Content-Type", contentType);//在头文件中设置转码
        NameValuePair[] data = {
                new NameValuePair("Uid", uid),
                new NameValuePair("Key", key),
                new NameValuePair("smsMob", phone),
                new NameValuePair("smsText", msg)};
        post.setRequestBody(data);
        try {
            client.executeMethod(post);
        } catch (Exception e) {
            logger.info("中国网建短信发送异常:", e);
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }
        String result;
        try {
            result = new String(post.getResponseBodyAsString().getBytes(charset));
            System.out.println(result); //打印返回消息状态
        } catch (Exception e) {
            logger.info("中国网建短信发送返回异常:", e);
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }

        post.releaseConnection();
        return result;
    }
}
