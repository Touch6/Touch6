package com.heqmentor.api.service;

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
public interface MobileService {

    /**检测手机号码是否被注册
     * @param mobile
     */
    void checkMobile(String mobile) throws Exception;

    /**
     * 生成手机验证码
     *
     * @param mobile 手机号
     * @return
     */
    String generateMobileCode(String mobile) throws Exception;
}
