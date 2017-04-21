package com.touch6.business.entity.system;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by PAX on 2017/4/21.
 */
public class Route {
    private Long routeId;
    @NotNull(message = "请指明路由等级")
    private Integer rank;
    private Long superId;
    @NotNull(message = "请指明路由的名称")
    private String name;
    @NotNull(message = "请指明具体的路由")
    private String uisref;
    @NotNull(message = "路由的描述信息不能为空")
    private String description;
    private Date createTime;
    private Date updateTime;

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUisref() {
        return uisref;
    }

    public void setUisref(String uisref) {
        this.uisref = uisref;
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

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
