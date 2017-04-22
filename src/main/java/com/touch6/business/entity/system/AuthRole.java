package com.touch6.business.entity.system;

/**
 * Created by LONG on 2017/4/18.
 */
public class AuthRole {
    private Long roleId;
    private String roleName;
    private Long authId;
    private String authName;
    private Long newAuthId;

    public AuthRole(){}
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

    public Long getNewAuthId() {
        return newAuthId;
    }

    public void setNewAuthId(Long newAuthId) {
        this.newAuthId = newAuthId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }
}
