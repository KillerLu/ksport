package com.killer.ksport.common.security.vo;

import javax.validation.constraints.NotNull;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:48
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class RequestLoginUser {

    @NotNull
    private String account;

    @NotNull
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
