package com.touch6.sm.gateway;

import com.touch6.core.exception.CoreException;
import com.touch6.enums.SmsGatewayInterface;

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
public class Touch6Test {
    public static void main(String[] args) throws CoreException {
        Touch6SmsUtil.sendSmsCode(SmsGatewayInterface.ALIYUN,"13880298929","266399");
    }
}
