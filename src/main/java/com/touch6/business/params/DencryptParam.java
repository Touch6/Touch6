package com.touch6.business.params;

import javax.validation.constraints.NotNull;

/**
 * Created by xuan.touch6@qq.com on 2017/4/16.
 */
public class DencryptParam {
    @NotNull(message = "未选择数据来源")
    private String dataFrom;
    @NotNull(message = "未选择进制")
    private String scale;
    @NotNull(message = "未输入数据")
    private String content;
    @NotNull(message = "未选择算法")
    private String arithmetic;
    private String salt;
    @NotNull(message = "未选择加密或解密")
    private String method;

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getArithmetic() {
        return arithmetic;
    }

    public void setArithmetic(String arithmetic) {
        this.arithmetic = arithmetic;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }
}
