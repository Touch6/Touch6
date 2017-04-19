package com.touch6.business.entity.system;

/**
 * Created by LONG on 2017/4/18.
 */
public class AuthRole {
    private Long roleId;
    private Long authId;

    public AuthRole(Long authId, Long roleId) {
        this.authId=authId;
        this.roleId=roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }
}
