package com.knowincloud.dto.entity;

import com.knowincloud.enums.MobileVerifyResult;

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
public class MobileCodeDto {
    private String id;
    private String mobile;
    private String prevCode;
    private String presCode;
    private Integer times;
    private Date prevTime;
    private Date presTime;
    private Integer verifyTimes;
    private MobileVerifyResult prevVerifyResult;
    private MobileVerifyResult presVerifyResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrevCode() {
        return prevCode;
    }

    public void setPrevCode(String prevCode) {
        this.prevCode = prevCode;
    }

    public String getPresCode() {
        return presCode;
    }

    public void setPresCode(String presCode) {
        this.presCode = presCode;
    }

    public MobileVerifyResult getPrevVerifyResult() {
        return prevVerifyResult;
    }

    public void setPrevVerifyResult(MobileVerifyResult prevVerifyResult) {
        this.prevVerifyResult = prevVerifyResult;
    }

    public MobileVerifyResult getPresVerifyResult() {
        return presVerifyResult;
    }

    public void setPresVerifyResult(MobileVerifyResult presVerifyResult) {
        this.presVerifyResult = presVerifyResult;
    }

    public Date getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(Date prevTime) {
        this.prevTime = prevTime;
    }

    public Date getPresTime() {
        return presTime;
    }

    public void setPresTime(Date presTime) {
        this.presTime = presTime;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getVerifyTimes() {
        return verifyTimes;
    }

    public void setVerifyTimes(Integer verifyTimes) {
        this.verifyTimes = verifyTimes;
    }
}
