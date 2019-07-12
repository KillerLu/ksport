package com.killer.ksport.user.db.dao;

import com.killer.ksport.common.core.db.dao.ksport.RoleDao;
import com.killer.ksport.common.core.db.view.ksport.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-11 下午2:15
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface RoleDaoExt extends RoleDao {

    public List<Role> listRoleByUser(@Param("userId") Long userId);
}
