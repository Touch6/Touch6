package com.touch6.business.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.touch6.business.api.service.ToolsService;
import com.touch6.business.output.DencryptOutput;
import com.touch6.business.params.DencryptParam;
import com.touch6.core.exception.CoreException;
import com.touch6.core.exception.ECodeUtil;
import com.touch6.core.exception.Error;
import com.touch6.core.exception.error.constant.CommonErrorConstant;
import com.touch6.core.exception.error.constant.SystemErrorConstant;
import com.touch6.utils.T6CodecUtils;
import com.touch6.utils.T6DateUtils;
import com.touch6.utils.T6PasswordEncryptionUtil;
import com.touch6.utils.T6ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.validation.Validator;
import java.security.MessageDigest;

/**
 * Created by PAX on 2017/4/1.
 */
@Service
public class ToolsServiceImpl implements ToolsService {

    @Autowired
    private Validator validator;

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

    @Override
    public DencryptOutput dencrypt(DencryptParam dencryptParam) {
        try {
            String error = T6ValidatorUtil.validate(validator, dencryptParam);
            if (error != null) {
                Error err = ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR);
                err.setDes(error);
                throw new CoreException(err);
            }
            DencryptOutput output = new DencryptOutput();
            switch (dencryptParam.getArithmetic()) {
                case "MD5BASE64":
                    if ("encrypt".equals(dencryptParam.getMethod())) {
                        BASE64Encoder encoder = new BASE64Encoder();

                        MessageDigest md = MessageDigest.getInstance("MD5", "SUN");
                        md.reset();
                        md.update(dencryptParam.getContent().getBytes("UTF-8"));
                        byte[] bytes = md.digest();
                        output.setOutput(encoder.encode(bytes));
                    } else if ("decrypt".equals(dencryptParam.getMethod())) {
                        throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
                    }
                    break;
                case "MD5":
                    break;
                case "SHA":
                    break;
                case "HMAC":
                    break;
                case "DES":
                    break;
                case "SALT":
//                if ("encrypt".equals(dencryptParam.getMethod())) {
//                    //加密
//                } else if ("decrypt".equals(dencryptParam.getMethod())) {
//                    //解密
//                    String password = T6PasswordEncryptionUtil.getEncryptedPassword(dencryptParam.getContent(), dencryptParam.getSalt());
//                    output.setOutput(password);
//                }
                    break;
                default:
                    throw new CoreException(ECodeUtil.getCommError(CommonErrorConstant.COMMON_PARAMS_ERROR));
            }
            return output;
        } catch (CoreException e) {
            throw e;
        } catch (Exception e) {
            throw new CoreException(ECodeUtil.getCommError(SystemErrorConstant.SYSTEM_EXCEPTION));
        }
    }
}
