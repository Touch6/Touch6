package com.touch6.business.params;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

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
public class RegisterParam {
    @NotNull(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号码长度为11位")
    private String phone;

    @NotNull(message = "验证码不能为空")
    @Length(min = 6, max = 18, message = "验证码为6位数字")
    private String code;

    @NotNull(message = "登录密码不能为空")
    @Length(min = 6, max = 24, message = "密码长度为6~24位长度")
    private String password;

    @NotNull(message = "确认密码不能为空")
    @Length(min = 6, max = 24, message = "确认密码长度为6~24位长度")
    private String confirmPassword;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
