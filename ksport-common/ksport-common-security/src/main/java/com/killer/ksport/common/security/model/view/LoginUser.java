package com.killer.ksport.common.security.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.killer.ksport.common.core.db.view.ksport.Permission;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.security.model.LoginDetail;
import com.killer.ksport.common.security.model.TokenDetail;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:27
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class LoginUser implements LoginDetail,TokenDetail {

    private Long userId;
    private String account;
    @JsonIgnore
    private String password;
    private List<Role> roles;
    private List<Permission> permissions;
    private boolean enable;

    public LoginUser(){

    }

    public LoginUser(Long userId, String account, String password, List<Role> roles, boolean enable) {
        this.userId = userId;
        this.account = account;
        this.password = password;
        this.roles = roles;
        this.enable = enable;
    }

    public LoginUser(Long userId, List<Role> roles) {
        this.userId = userId;
        this.roles = roles;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
