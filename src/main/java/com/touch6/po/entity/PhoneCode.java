package com.touch6.po.entity;

import com.touch6.enums.PhoneVerifyResult;

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
public class PhoneCode {
    private String id;
    private String phone;
    private String prevCode;
    private String presCode;
    private Integer times;
    private Date prevTime;
    private Date presTime;
    private Integer verifyTimes;
    private PhoneVerifyResult prevVerifyResult;
    private PhoneVerifyResult presVerifyResult;
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private String reserve4;
    private String reserve5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4;
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5;
    }

    public PhoneVerifyResult getPrevVerifyResult() {
        return prevVerifyResult;
    }

    public void setPrevVerifyResult(PhoneVerifyResult prevVerifyResult) {
        this.prevVerifyResult = prevVerifyResult;
    }

    public PhoneVerifyResult getPresVerifyResult() {
        return presVerifyResult;
    }

    public void setPresVerifyResult(PhoneVerifyResult presVerifyResult) {
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
