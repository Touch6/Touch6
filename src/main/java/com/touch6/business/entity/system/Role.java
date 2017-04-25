package com.touch6.business.entity.system;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by LONG on 2017/4/18.
 */
public class Role {
    private Long roleId;
    @NotNull(message = "请指明角色名称")
    private String name;
    private Integer locked = 0;
    private Date createTime;
    private Date updateTime;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
