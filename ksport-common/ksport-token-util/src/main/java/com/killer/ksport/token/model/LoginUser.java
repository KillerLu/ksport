package com.killer.ksport.token.model;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:27
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class LoginUser {

    private Long userId;
    private String account;
    private List<String> roles;
    private List<String> permissions;
    private boolean enable;

    public LoginUser(){}

    public LoginUser(Long userId, String account, boolean enable) {
        this.userId = userId;
        this.account = account;
        this.enable = enable;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
