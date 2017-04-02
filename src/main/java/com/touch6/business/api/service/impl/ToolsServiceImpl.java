package com.touch6.business.api.service.impl;

import com.touch6.business.api.service.ToolsService;
import com.touch6.utils.DateUtil;
import org.springframework.stereotype.Service;

/**
 * Created by PAX on 2017/4/1.
 */
@Service
public class ToolsServiceImpl implements ToolsService {


    @Override
    public String dateFormat(String src, String format,String dst,String type) {
        if("1".equals(type)){
            return DateUtil.dateFormat(src, format);
        }else if("-1".equals(type)){
            return DateUtil.dateParse(dst, format).toString();
        }
        return null;
    }
}
