package com.touch6.business.entity.system;

/**
 * Created by LONG on 2017/4/18.
 */
public class AuthMenu {
    private Long authId;
    private Long menuId;
    private Long newAuthId;

    public AuthMenu(){}
    public AuthMenu(Long authId, Long menuId) {
        this.authId=authId;
        this.menuId=menuId;
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

    public Long getNewAuthId() {
        return newAuthId;
    }

    public void setNewAuthId(Long newAuthId) {
        this.newAuthId = newAuthId;
    }
}
