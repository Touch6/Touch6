package com.touch6.business.output.system;

/**
 * Created by LONG on 2017/4/23.
 */
public class ModuleSelectList {
    private Long moduleId;
    private Integer sort;
    private String name;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
