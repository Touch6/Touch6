package com.touch6.business.entity.system;

import java.util.Date;

/**
 * Created by LONG on 2017/4/18.
 */
public class Module {
    private Long moduleId;
    private String name;
    private String className;
    private String attrLink;
    private Date createTime;
    private Date updateTime;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAttrLink() {
        return attrLink;
    }

    public void setAttrLink(String attrLink) {
        this.attrLink = attrLink;
    }
}
