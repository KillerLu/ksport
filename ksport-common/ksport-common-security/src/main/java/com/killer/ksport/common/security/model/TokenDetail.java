package com.killer.ksport.common.security.model;

import com.killer.ksport.common.core.db.view.ksport.Role;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:20
 * @description：令牌信息
 * @modified By：
 * @version: version
 */
public interface TokenDetail {

    /**
     * 获取用户id
     * @return
     */
    Long getUserId();

    /**
     * 获取角色
     * @return
     */
    List<Role> getRoles();
}
