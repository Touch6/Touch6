package com.touch6.business.api.service;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.output.DencryptOutput;
import com.touch6.business.params.DencryptParam;

/**
 * Created by PAX on 2017/4/1.
 */
public interface ToolsService {

    String dateFormat(String src, String format, String type);

    String codec(String src, String type);

    DencryptOutput dencrypt(DencryptParam dencryptParam);
}
