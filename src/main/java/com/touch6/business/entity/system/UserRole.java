package com.touch6.business.entity.system;

/**
 * Created by LONG on 2017/4/18.
 */
public class UserRole {
    private Long userId;
    private String userName;
    private Long roleId;
    private String roleName;
    private Long newRoleId;

    public UserRole(){}
    public UserRole(Long userId, Long roleId) {
        this.userId=userId;
        this.roleId=roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getNewRoleId() {
        return newRoleId;
    }

    public void setNewRoleId(Long newRoleId) {
        this.newRoleId = newRoleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
