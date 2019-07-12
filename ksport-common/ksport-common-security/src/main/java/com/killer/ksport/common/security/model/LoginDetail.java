package com.killer.ksport.common.security.model;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:16
 * @description：登录信息
 * @modified By：
 * @version: version
 */
public interface LoginDetail {

    /**
     * 获取用户名
     * @return
     */
    String getAccount();

    /**
     * 获取密码
     * @return
     */
    String getPassword();

    /**
     * 是否可登录
     * @return
     */
    boolean isEnable();
}
