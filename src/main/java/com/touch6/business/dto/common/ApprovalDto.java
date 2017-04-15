package com.touch6.business.dto.common;

import com.touch6.business.enums.TargetObject;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by xuan.touch6@qq.com on 2017/4/13.
 */
public class ApprovalDto {
    @NotNull(message = "未指明操作对象")
    private String objectId;
    @NotNull(message = "未指明操作人")
    @Length(min = 32, max = 32, message = "参数长度不正确")
    private String who;
    private TargetObject targetObject;
    private Date createTime;
    private Date updateTime;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public TargetObject getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
