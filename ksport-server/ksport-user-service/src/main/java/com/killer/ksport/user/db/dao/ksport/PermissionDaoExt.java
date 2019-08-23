package com.killer.ksport.user.db.dao.ksport;

import com.killer.ksport.common.core.db.dao.ksport.PermissionDao;
import com.killer.ksport.common.core.db.view.ksport.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：KillerGrou
 * @date ：Created in 19-7-11 下午3:22
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface PermissionDaoExt extends PermissionDao{
    List<Permission> listPermissionByUser(@Param("userId") Long userId);
}
