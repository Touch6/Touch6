package com.knowincloud.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

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
 * 2017/3/1  	         zhuxl@paxsz.com        Create/Add/Modify/Delete
 * ============================================================================		
 */
public class ValidatorUtil {
    public static final String validate(Validator validator, Object clazz) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(clazz);
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation cv : constraintViolations) {
            String s = cv.getMessage();
            msg.append(s + ";");
        }
        return msg.length() == 0 ? null : msg.toString();
    }
}
