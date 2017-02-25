package com.qingsb.util;

import java.util.Random;
import java.util.UUID;

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
 * 2017/2/22  	         zhuxl@paxsz.com        Create/Add/Modify/Delete
 * ============================================================================		
 */
public class StringUtil {
    public static final int[] digits={0,1,2,3,4,5,6,7,8,9};
    public static final String generate32uuid() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replaceAll("-", "");
    }

    public static final String generate6MobileCode(){
        StringBuilder code=new StringBuilder();
        for(int i=0;i<6;i++){
            int index=new Random().nextInt(10);
            code.append(digits[index]);
        }
        return code.toString();
    }

    public static void main(String[] args) {
        Integer integer=new Integer(123);
        System.out.println(integer>6);
        System.out.println(integer.intValue()>6);
    }
}
