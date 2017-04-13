package com.touch6.business.entity.common;

import com.touch6.business.enums.TargetObject;

import java.util.Date;

/**
 * Created by xuan.touch6@qq.com on 2017/4/13.
 */
public class Oppose {
    private String objectId;
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
