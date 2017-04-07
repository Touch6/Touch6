package com.touch6.business.api.service.impl;

import com.touch6.business.api.service.ToolsService;
import com.touch6.utils.T6CodecUtils;
import com.touch6.utils.T6DateUtils;
import org.springframework.stereotype.Service;

/**
 * Created by PAX on 2017/4/1.
 */
@Service
public class ToolsServiceImpl implements ToolsService {


    @Override
    public String dateFormat(String src, String format, String type) {
        if ("1".equals(type)) {
            return T6DateUtils.dateFormat(src, format);
        } else if ("-1".equals(type)) {
            return T6DateUtils.dateParse(src, format).toString();
        }
        return null;
    }

    @Override
    public String codec(String src, String type) {
        switch (type) {
            case "1":
                //URL编码(UTF-8)
                return T6CodecUtils.URLEncode(src, "UTF-8");
            case "2":
                //URL解码(UTF-8)
                return T6CodecUtils.URLDecode(src, "UTF-8");
            case "3":
                //URL编码(GBK)
                return T6CodecUtils.URLEncode(src, "GBK");
            case "4":
                //URL解码(GBK)
                return T6CodecUtils.URLDecode(src, "GBK");
            default:
                return null;
        }
    }
}
