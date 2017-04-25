package com.touch6.business.entity.system;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by LONG on 2017/4/18.
 */
public class Auth {
    private Long authId;
    @NotNull(message = "请指明权限名称")
    private String name;
    private Integer locked=0;
    private Date createTime;
    private Date updateTime;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }
}
