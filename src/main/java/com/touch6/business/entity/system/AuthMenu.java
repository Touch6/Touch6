package com.touch6.business.entity.system;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LONG on 2017/4/18.
 */
public class AuthMenu {
    private Long authId;
    private Long menuId;
    private String authName;
    private String menuName;
    private List<JSONObject> authList = new ArrayList<>();

    public AuthMenu() {
    }

    public AuthMenu(Long authId, Long menuId) {
        this.authId = authId;
        this.menuId = menuId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<JSONObject> getAuthList() {
        return authList;
    }

    public void setAuthList(List<JSONObject> authList) {
        this.authList = authList;
    }
}
