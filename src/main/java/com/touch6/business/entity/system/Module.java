package com.touch6.business.entity.system;

import com.google.common.collect.Lists;
import com.touch6.commons.PageObject;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public class Module {
    private Long moduleId;
    @NotNull(message = "请指明模块名称")
    private String name;
    private String className;
    private String attrLink;
    @NotNull(message = "请指明排序序号")
    private Integer sort;
    private Integer locked = 0;
    private Date createTime;
    private Date updateTime;
    private PageObject<Menu> menuPageObj=new PageObject<>();
    private List<Menu> menuList= Lists.newArrayList();

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

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public PageObject<Menu> getMenuPageObj() {
        return menuPageObj;
    }

    public void setMenuPageObj(PageObject<Menu> menuPageObj) {
        this.menuPageObj = menuPageObj;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }
}
