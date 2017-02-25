package com.qingsb.core.info;

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
public class Success {
    private int code;
    private String info;
    private String des;

    public Success() {
        this.code = 200;
        this.info = null;
        this.des = "操作成功";
    }

    public Success(int code, String info, String des) {
        this.code = code;
        this.info = info;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
